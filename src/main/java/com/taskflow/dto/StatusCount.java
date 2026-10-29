package com.taskflow.dto;

import com.taskflow.model.TaskStatus;

public record StatusCount(TaskStatus status, long count) {
}
