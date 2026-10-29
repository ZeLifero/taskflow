package com.taskflow.strategy;

import com.taskflow.algo.Sorting;
import com.taskflow.model.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class DueDateSortStrategy implements TaskSortStrategy {

    @Override
    public String name() {
        return "dueDate";
    }

    @Override
    public List<Task> sort(List<Task> tasks) {
        // earliest first, tasks without a due date go last
        Comparator<Task> cmp = Comparator.comparing(Task::getDueDate,
                Comparator.nullsLast(Comparator.<LocalDate>naturalOrder()));
        return Sorting.mergeSort(tasks, cmp);
    }
}
