package com.planner.Events;

import com.planner.DateManager;

import java.text.ParseException;

public class Event {
    private String title;
    private String description;
    private String titleHeader;
    private String descriptionHeader;
    private long startTime;
    private long endTime;

    public Event(Event event) {
        this.title = event.title;
        this.description = event.description;
        this.startTime = event.startTime;
        this.endTime = event.endTime;
        titleHeader = "Title:";
        descriptionHeader = "Description:";
    }

    public Event() {
        titleHeader = "Title:";
        descriptionHeader = "Description:";
    }

    public static Event getDefaultEvent() {
        return new Event(
                "Default event",
                "It seems you don't have any events planned yet... why not plan one now?",
                0,
                0);
    }

    public Event(String title, String description, long startTime, long endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String toString(String type) {
        try {
            return " %s %10s: %s".formatted(DateManager.toFormattedDate(startTime), type, title);

        } catch (ParseException e) {

            try {
                return " %s %10s: %s".formatted(DateManager.toFormattedDate(0L), type, title);
            } catch (ParseException ignored) {
                return "Error";
            }
        }
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

    public String getDescriptionHeader() {
        return descriptionHeader;
    }

    public void setDescriptionHeader(String descriptionHeader) {
        this.descriptionHeader = descriptionHeader;
    }

    public String getTitleHeader() {
        return titleHeader;
    }

    public void setTitleHeader(String titleHeader) {
        this.titleHeader = titleHeader;
    }
}
