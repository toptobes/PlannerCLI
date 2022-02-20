package com.planner.event;

import java.io.Serializable;

public enum EventType implements Serializable {
    EVENT("Event", "Title: ", "Description: ", true, true),
    REMINDER("Reminder", "", "Description: ", false, false),
    DAILY_REMINDER("Reminder", "", "Description: ", false, false),
    TODO("TODO", "Quick Description: ", "Todo: ", true, true);

    private final String name;
    private final String titleHeader;
    private final String descriptionHeader;
    private final boolean hasEndTime;
    private final boolean hasTitle;

    private static final long serialVersionUID = 2L;

    EventType(String name, String titleHeader, String descriptionHeader, boolean hasEndTime, boolean hasTitle) {
        this.name = name;
        this.titleHeader = titleHeader;
        this.descriptionHeader = descriptionHeader;
        this.hasEndTime = hasEndTime;
        this.hasTitle = hasTitle;
    }

    public String getName() {
        return name;
    }

    public String getTitleHeader() {
        return titleHeader;
    }

    public String getDescriptionHeader() {
        return descriptionHeader;
    }

    public boolean hasEndTime() {
        return hasEndTime;
    }

    public boolean hasTitle() {
        return hasTitle;
    }
}
