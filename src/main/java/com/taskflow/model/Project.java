package com.taskflow.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    // One project -> many tasks. The owning side is Task.project.
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    protected Project() { // required by JPA
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Keeps both sides of the relationship in sync. */
    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Task> getTasks() { return Collections.unmodifiableList(tasks); }
}
