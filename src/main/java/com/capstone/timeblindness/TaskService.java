package com.capstone.timeblindness;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public void saveTask(Task task) {
        this.taskRepo.save(task);
    }

    public Task getTaskById(Long id) {
        Optional<Task> optional = taskRepo.findById(id);
        Task task = null;
        if (optional.isPresent()) {
            task = optional.get();
        } else {
            throw new RuntimeException("Task not found for id " + id);
        }
        return task;
    }

    public void deleteTaskById(Long id) {
        this.taskRepo.deleteById(id);
    }

    public Set<Task> getByUserId(Long id) {
        return getAllTasks().stream()
            .filter(task -> task.getUser().getId().equals(id))
            .collect(Collectors.toSet());
    }
}
