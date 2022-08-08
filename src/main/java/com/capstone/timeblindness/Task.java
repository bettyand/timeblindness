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
    private int numAttempts = 0;

    @Column(name = "total_time")
    private Long totalTime = 0L;

    @Column(name = "shortest_time")
    private Long shortestTime = 0L;

    @Column(name = "longest_time")
    private Long longestTime = 0L;

    @Column(name = "average_time")
    private Long averageTime = 0L;

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
        this.numAttempts = numAttempts + 1;
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

    public String shortestTimeString() {
        int totalSeconds = (int)(this.shortestTime / 1000);
        int hours = totalSeconds / 3600;
        int remainingMinutes = totalSeconds % 3600;
        int minutes = remainingMinutes / 60;
        int seconds = remainingMinutes % 60;
        String string;
        if (hours < 10) {
            string = "0" + hours + ":";
        } else {
            string = hours + ":";
        }
        if (minutes < 10) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds < 10) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }

    public String longestTimeString() {
        int totalSeconds = (int)(this.longestTime / 1000);
        int hours = totalSeconds / 3600;
        int remainingMinutes = totalSeconds % 3600;
        int minutes = remainingMinutes / 60;
        int seconds = remainingMinutes % 60;
        String string;
        if (hours < 10) {
            string = "0" + hours + ":";
        } else {
            string = hours + ":";
        }
        if (minutes < 10) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds < 10) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }

    public String averageTimeString() {
        int totalSeconds = (int)(this.averageTime / 1000);
        int hours = totalSeconds / 3600;
        int remainingMinutes = totalSeconds % 3600;
        int minutes = remainingMinutes / 60;
        int seconds = remainingMinutes % 60;
        String string;
        if (hours < 10) {
            string = "0" + hours + ":";
        } else {
            string = hours + ":";
        }
        if (minutes < 10) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds < 10) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }
}
