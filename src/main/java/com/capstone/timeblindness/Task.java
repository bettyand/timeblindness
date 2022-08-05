package com.capstone.timeblindness;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "number_of_attempts")
    private int numAttempts;

    @Column(name = "total_time")
    private Long totalTime;

    @Column(name = "shortest_time")
    private Long shortestTime;

    @Column(name = "longest_time")
    private Long longestTime;

    @Column(name = "average_time")
    private Long averageTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Attempt> attempts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public void setNumAttempts(int numAttempts) {
        this.numAttempts = numAttempts;
    }

    public void incrementNumAttempts() {
        this.numAttempts = numAttempts++;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public void addToTotalTime(Long newTime) {
        this.totalTime = totalTime + newTime;
    }

    public Long getShortestTime() {
        return shortestTime;
    }

    public void setShortestTime(Long shortestTime) {
        this.shortestTime = shortestTime;
    }

    public Long getLongestTime() {
        return longestTime;
    }

    public void setLongestTime(Long longestTime) {
        this.longestTime = longestTime;
    }

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }

    public void updateAverageTime() {
        this.averageTime = totalTime / numAttempts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(Set<Attempt> attempts) {
        this.attempts = attempts;
    }
}
