package com.taskflow.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    @Test
    void allowedTransitions() {
        assertTrue(TaskStatus.TODO.canMoveTo(TaskStatus.IN_PROGRESS));
        assertTrue(TaskStatus.IN_PROGRESS.canMoveTo(TaskStatus.DONE));
    }

    @Test
    void forbiddenTransitions() {
        assertFalse(TaskStatus.TODO.canMoveTo(TaskStatus.DONE));
        assertFalse(TaskStatus.DONE.canMoveTo(TaskStatus.TODO));
    }

    @Test
    void taskRejectsIllegalMove() {
        Task task = new Task("t", null, Priority.LOW, null);
        assertThrows(IllegalStateException.class, () -> task.moveTo(TaskStatus.DONE));
    }
}
