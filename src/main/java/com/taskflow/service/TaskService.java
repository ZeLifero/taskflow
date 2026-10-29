package com.taskflow.service;

import com.taskflow.dto.CreateTaskRequest;
import com.taskflow.dto.StatusCount;
import com.taskflow.dto.TaskDto;
import com.taskflow.exception.NotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Tag;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TagRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.strategy.TaskSortStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final String DEFAULT_SORT = "priority";

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final Map<String, TaskSortStrategy> strategies;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       TagRepository tagRepository,
                       List<TaskSortStrategy> strategies) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(TaskSortStrategy::name, Function.identity()));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAll(String sortBy) {
        String key = (sortBy == null || sortBy.isBlank()) ? DEFAULT_SORT : sortBy;
        TaskSortStrategy strategy = strategies.get(key);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown sort '" + key + "', available: " + strategies.keySet());
        }
        return strategy.sort(taskRepository.findAllWithDetails()).stream()
                .map(TaskDto::from)
                .toList();
    }

    @Transactional
    public TaskDto create(CreateTaskRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new NotFoundException("Project not found: " + request.projectId()));

        Task task = new Task(request.title(), request.description(), request.priority(), request.dueDate());
        project.addTask(task);

        if (request.tags() != null) {
            for (String raw : request.tags()) {
                String name = raw.trim().toLowerCase(Locale.ROOT);
                if (name.isEmpty()) continue;
                Tag tag = tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(new Tag(name)));
                task.getTags().add(tag);
            }
        }
        return TaskDto.from(taskRepository.save(task));
    }

    @Transactional
    public TaskDto changeStatus(Long id, TaskStatus next) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
        task.moveTo(next); // dirty checking: Hibernate issues the UPDATE on commit
        return TaskDto.from(task);
    }

    @Transactional
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StatusCount> stats() {
        return taskRepository.countByStatus();
    }
}
