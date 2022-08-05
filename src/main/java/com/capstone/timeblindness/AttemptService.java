package com.capstone.timeblindness;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {
    @Autowired
    private AttemptRepository attemptRepo;

    public List<Attempt> getAllAttempts() {
        return attemptRepo.findAll();
    }

    public void saveAttempt(Attempt attempt) {
        attempt.calcDuration();
        this.attemptRepo.save(attempt);
    }

    public Attempt getAttemptById(Long id) {
        Optional<Attempt> optional = attemptRepo.findById(id);
        Attempt attempt = null;
        if (optional.isPresent()) {
            attempt = optional.get();
        } else {
            throw new RuntimeException("Attempt not found for id " + id);
        }
        return attempt;
    }

    public void deleteAttemptById(Long id) {
        this.attemptRepo.deleteById(id);
    }

    public Set<Attempt> getByTaskId(Long id) {
        return getAllAttempts().stream()
            .filter(attempt -> attempt.getTask().getId().equals(id))
            .collect(Collectors.toSet());
    }
}
