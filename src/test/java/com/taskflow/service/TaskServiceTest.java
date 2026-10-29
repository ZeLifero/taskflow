package com.taskflow.service;

import com.taskflow.dto.CreateTaskRequest;
import com.taskflow.dto.TaskDto;
import com.taskflow.exception.NotFoundException;
import com.taskflow.model.*;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TagRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.strategy.DueDateSortStrategy;
import com.taskflow.strategy.PrioritySortStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock TaskRepository taskRepository;
    @Mock ProjectRepository projectRepository;
    @Mock TagRepository tagRepository;

    TaskService service;
    Project project;

    @BeforeEach
    void setUp() {
        service = new TaskService(taskRepository, projectRepository, tagRepository,
                List.of(new PrioritySortStrategy(), new DueDateSortStrategy()));
        project = new Project("P", null);
    }

    private Task task(String title, Priority priority) {
        Task t = new Task(title, null, priority, null);
        project.addTask(t);
        return t;
    }

    @Test
    void findAll_sortsByPriorityHighestFirst() {
        when(taskRepository.findAllWithDetails()).thenReturn(
                List.of(task("low", Priority.LOW), task("high", Priority.HIGH), task("mid", Priority.MEDIUM)));

        List<String> titles = service.findAll("priority").stream().map(TaskDto::getTitle).toList();

        assertEquals(List.of("high", "mid", "low"), titles);
    }

    @Test
    void findAll_unknownSort_throws() {
        assertThrows(IllegalArgumentException.class, () -> service.findAll("nonsense"));
    }

    @Test
    void changeStatus_illegalTransition_throws() {
        Task t = task("t", Priority.LOW);
        t.moveTo(TaskStatus.IN_PROGRESS);
        t.moveTo(TaskStatus.DONE);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(t));

        assertThrows(IllegalStateException.class, () -> service.changeStatus(1L, TaskStatus.TODO));
    }

    @Test
    void create_unknownProject_throwsNotFound() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());
        CreateTaskRequest request = new CreateTaskRequest("x", null, Priority.LOW, null, 99L, null);

        assertThrows(NotFoundException.class, () -> service.create(request));
    }

    @Test
    void create_normalizesTagNames() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(tagRepository.findByName("java")).thenReturn(Optional.empty());
        when(tagRepository.save(any(Tag.class))).thenAnswer(inv -> inv.getArgument(0));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
        CreateTaskRequest request = new CreateTaskRequest("x", null, Priority.HIGH, null, 1L, Set.of("  Java "));

        TaskDto result = service.create(request);

        assertEquals(List.of("java"), result.getTags());
    }
}
