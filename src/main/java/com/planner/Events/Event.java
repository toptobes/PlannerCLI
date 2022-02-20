package com.planner.Events;

import com.planner.DateManager;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record Event(Type type,
                    String title,
                    String description,
                    long startTime,
                    long endTime
) implements Comparable<Event>, Serializable {

    public String toString() {
        return " %s %10s: %s".formatted(new DateManager().toFormattedDate(startTime), type, title);
    }

    @Override
    public int compareTo(@NotNull Event o) {
        return (startTime > o.startTime) ? 1 : -1;
    }

    public static Event getDefaultEvent() {
        return new Event(
                Type.EVENT,
                "Default event",
                "It seems you don't have any events planned yet... why not plan one now?",
                0L,
                0L);
    }
}
