package com.taskflow.strategy;

import com.taskflow.algo.Sorting;
import com.taskflow.model.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PrioritySortStrategy implements TaskSortStrategy {

    @Override
    public String name() {
        return "priority";
    }

    @Override
    public List<Task> sort(List<Task> tasks) {
        // highest priority first
        Comparator<Task> cmp = Comparator.comparingInt((Task t) -> t.getPriority().getWeight()).reversed();
        return Sorting.mergeSort(tasks, cmp);
    }
}
