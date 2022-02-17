package com.planner.Events;

public class Reminder extends Event {
    public Reminder(Event event) {
        super(event);
        setTitleHeader("Reminder:");
        setDescriptionHeader("Description:");
    }
}
