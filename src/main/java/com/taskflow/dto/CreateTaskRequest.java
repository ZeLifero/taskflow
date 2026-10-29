package com.taskflow.dto;

import com.taskflow.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record CreateTaskRequest(
        @NotBlank @Size(max = 200) String title,
        @Size(max = 1000) String description,
        @NotNull Priority priority,
        LocalDate dueDate,
        @NotNull Long projectId,
        Set<String> tags) {
}
