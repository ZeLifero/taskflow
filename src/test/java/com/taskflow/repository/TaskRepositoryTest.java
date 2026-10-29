package com.taskflow.repository;

import com.taskflow.dto.StatusCount;
import com.taskflow.model.*;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Runs against in-memory H2: tests real mappings and queries without needing PostgreSQL. */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired ProjectRepository projectRepository;
    @Autowired TaskRepository taskRepository;
    @Autowired TestEntityManager em;

    private void projectWithTwoTasks() {
        Project p = projectRepository.save(new Project("P", null));
        Task a = new Task("a", null, Priority.HIGH, null);
        Task b = new Task("b", null, Priority.LOW, null);
        p.addTask(a);
        p.addTask(b);
        b.moveTo(TaskStatus.IN_PROGRESS);
        taskRepository.saveAll(List.of(a, b));
    }

    @Test
    void findByStatus_returnsOnlyMatchingTasks() {
        projectWithTwoTasks();
        em.flush();
        em.clear();

        List<Task> result = taskRepository.findByStatus(TaskStatus.IN_PROGRESS);

        assertEquals(1, result.size());
        assertEquals("b", result.get(0).getTitle());
    }

    @Test
    void findAllWithDetails_fetchesProjectAndTagsEagerly() {
        projectWithTwoTasks();
        Task a = taskRepository.findByStatus(TaskStatus.TODO).get(0);
        a.getTags().add(em.persist(new Tag("java")));
        em.flush();
        em.clear();

        List<Task> result = taskRepository.findAllWithDetails();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> Hibernate.isInitialized(t.getProject())));
    }

    @Test
    void countByStatus_groupsRows() {
        projectWithTwoTasks();
        em.flush();
        em.clear();

        List<StatusCount> counts = taskRepository.countByStatus();

        assertEquals(2, counts.size());
        assertEquals(2L, counts.stream().mapToLong(StatusCount::count).sum());
    }
}
