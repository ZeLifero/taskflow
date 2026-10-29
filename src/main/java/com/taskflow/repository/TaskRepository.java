package com.taskflow.repository;

import com.taskflow.dto.StatusCount;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Derived query: Spring Data builds the SQL from the method name
    List<Task> findByStatus(TaskStatus status);

    // JPQL with fetch joins: loads project and tags in ONE query (avoids the N+1 problem)
    @Query("select distinct t from Task t join fetch t.project left join fetch t.tags")
    List<Task> findAllWithDetails();

    // Aggregation with GROUP BY
    @Query("select new com.taskflow.dto.StatusCount(t.status, count(t)) from Task t group by t.status")
    List<StatusCount> countByStatus();
}
