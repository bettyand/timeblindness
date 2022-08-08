package com.capstone.timeblindness;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public Page<Task> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortField).ascending() 
            : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.taskRepo.findAll(pageable);
    }

    public Page<Task> findPaginatedByUserId(int pageNo, int pageSize, String sortField, String sortDirection, Long id) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortField).ascending() 
            : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.taskRepo.findByUserId(id, pageable);
    }
}
