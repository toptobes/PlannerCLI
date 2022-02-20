package com.planner.Events;

public enum Type {
    EVENT("Title:", "Description:", true),
    REMINDER("Reminder:", "Description:", false),
    DAILY_REMINDER("Reminder:", "Description:", false),
    TODO("Quick Description:", "Todo:", true);

    private final String titleHeader;
    private final String descriptionHeader;
    private final boolean hasEndTime;

    Type(String title, String description, boolean hasEndTime) {
        this.titleHeader = title;
        this.descriptionHeader = description;
        this.hasEndTime = hasEndTime;
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
}
