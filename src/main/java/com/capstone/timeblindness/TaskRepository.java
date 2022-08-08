package com.capstone.timeblindness;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Page<Task> findByUserId(Long id, Pageable pageable);
}
