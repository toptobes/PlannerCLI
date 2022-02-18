package com.planner.Events;

public class Reminder extends Event {
    public Reminder(Event event) {
        super(event);
        titleHeader = "Reminder:";
        descriptionHeader = "Description:";
        type = "Reminder";
    }
}
