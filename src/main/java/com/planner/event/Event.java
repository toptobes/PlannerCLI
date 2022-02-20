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

    @Override
    public String toString() {
        String title = cutStringOffAt(type.getTitleHeader(), 11, ": ") + this.title;
        String description = cutStringOffAt(type.getDescriptionHeader(), 5, ": ") + this.description;

        return String.format(
                " %s %-10s %-33s %s",
                new DateManager().toFormattedDate(startTime),
                type.getName() + ":",
                format(title, 35),
                format(description, 42));
    }

    private String format(String string, int length) {
        return cutStringOffAt(string, length, "…").replace("\n", "\\").trim();
    }

    private String cutStringOffAt(String string, int length, String s) {
        if (string.length() <= length) {
            return string;
        }

        String cutOffString = string.substring(0, length - 1);

        if (cutOffString.endsWith(" ")) {
            return cutOffString.substring(0, cutOffString.length() - 2) + s;
        }

        return cutOffString + s;
    }
}
