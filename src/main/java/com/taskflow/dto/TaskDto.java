package com.taskflow.dto;

import com.taskflow.model.Priority;
import com.taskflow.model.Tag;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;

import java.time.LocalDate;
import java.util.List;

/** Read model returned to clients. Entities never leave the service layer. */
public final class TaskDto {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final Priority priority;
    private final LocalDate dueDate;
    private final String projectName;
    private final List<String> tags;

    private TaskDto(Task t) {
        this.id = t.getId();
        this.title = t.getTitle();
        this.description = t.getDescription();
        this.status = t.getStatus();
        this.priority = t.getPriority();
        this.dueDate = t.getDueDate();
        this.projectName = t.getProject().getName();
        this.tags = t.getTags().stream().map(Tag::getName).sorted().toList();
    }

    public static TaskDto from(Task task) {
        return new TaskDto(task);
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public String getProjectName() { return projectName; }
    public List<String> getTags() { return tags; }
}
