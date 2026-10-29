package com.taskflow.strategy;

import com.taskflow.model.Task;

import java.util.List;

/** Strategy pattern: Spring injects all implementations, the service picks one by name. */
public interface TaskSortStrategy {
    String name();

    List<Task> sort(List<Task> tasks);
}
