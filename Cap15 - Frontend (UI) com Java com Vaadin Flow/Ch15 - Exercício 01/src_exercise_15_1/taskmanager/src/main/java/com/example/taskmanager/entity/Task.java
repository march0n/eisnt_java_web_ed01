package com.example.taskmanager.entity;

public class Task {
    private Long id;
    private String name;
    private boolean completed;

    public Task(Long id, String name) {
        this.id = id;
        this.name = name;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
