package com.capstone.timeblindness;

import javax.persistence.*;
import java.lang.System;
import java.util.Date;

@Entity
@Table(name = "attempts")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "stop_time")
    private Long stopTime;

    private Long duration;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Task task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
    }

    public void calcDuration() {
        this.duration = stopTime - startTime;
    }

    public String startTimeString() {
        if (this.startTime == null) {
            return "n/a";
        }
        return new Date(this.startTime).toString();
    }

    public String stopTimeString() {
        if (this.stopTime == null) {
            return "n/a";
        }
        return new Date(this.startTime).toString();
    }

    public String durationString() {
        if (this.duration == null) {
            return "n/a";
        }
        int totalSeconds = (int)(this.duration / 1000);
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
