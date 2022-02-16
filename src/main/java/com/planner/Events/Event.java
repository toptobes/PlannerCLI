package com.planner.Events;

public class Event {
    private String title;
    private String description;
    private long startTime;
    private long endTime;

    public Event(Event event) {
        this.title = event.title;
        this.description = event.description;
        this.startTime = event.startTime;
        this.endTime = event.endTime;
    }

    public Event() {

    }

    public Event build() {
        if (endTime == 0) endTime = startTime;
        if (description == null) description = "";

        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
