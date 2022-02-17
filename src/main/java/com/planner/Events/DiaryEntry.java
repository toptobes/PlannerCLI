package com.planner.Events;

public class DiaryEntry extends Event {
    public DiaryEntry(Event event) {
        super(event);
        setTitleHeader("Title:");
        setDescriptionHeader("Entry:");
    }
}
