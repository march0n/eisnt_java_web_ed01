package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        tasks.add(new Task(1L, "Aprender Vaadin"));
        tasks.add(new Task(2L, "Criar uma aplicação Spring Boot"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTaskById(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void toggleTaskCompletion(Task task) {
        Task existingTask = getTaskById(task.getId());
        if (existingTask != null) {
            existingTask.setCompleted(!existingTask.isCompleted());
        }
    }
}
