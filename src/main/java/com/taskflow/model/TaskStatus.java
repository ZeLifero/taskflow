package com.taskflow.model;

/** Task lifecycle. Allowed transitions live in the enum itself (encapsulation). */
public enum TaskStatus {
    TODO, IN_PROGRESS, DONE;

    public boolean canMoveTo(TaskStatus next) {
        return switch (this) {
            case TODO -> next == IN_PROGRESS;
            case IN_PROGRESS -> next == DONE || next == TODO;
            case DONE -> false;
        };
    }
}
