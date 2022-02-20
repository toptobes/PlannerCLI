package com.planner.event;

import com.planner.utility.DateManager;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

public record Event(EventType type,
                    String title,
                    String description,
                    long startTime,
                    long endTime
) implements Comparable<Event>, Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    public String toString() {
        String title = type.getTitleHeader() + " " + this.title;
        String description = type.getDescriptionHeader() + " " + this.description;

        return " %s %-8s %-33s %-42s".formatted(
                new DateManager().toFormattedDate(startTime),
                type + ":",
                title.substring(0, Math.min(33, title.length())) + ((title.length() > 33) ? "…" : ""),
                description.substring(0, Math.min(47, description.length())) + ((description.length() > 47) ? "…" : ""));
    }

    @Override
    public int compareTo(@NotNull Event o) {
        return (startTime > o.startTime) ? 1 : -1;
    }

    public static Event getDefaultEvent() {
        return new Event(
                EventType.EVENT,
                "Nothing here!",
                "It seems you don't have any events planned yet... why not plan one now?",
                0L,
                0L);
    }
}
