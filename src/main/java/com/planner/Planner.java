package com.planner;

import com.planner.Events.Event;
import com.planner.Events.Reminder;
import com.planner.Events.Todo;

import java.util.TreeMap;

public class Planner {
    private static final TreeMap<Long, ? super Event> planner = new TreeMap<>();

    public static <E extends Event> void addEvent(E event) {
        if (event == null) {
            return;
        }

        planner.put(event.getStartTime(), event);
    }

    public static <E extends Event> String getNextReminder() {
        String lastTodo = null, lastEvent = null;

        for (Object value : planner.values()) {

            if (value instanceof Reminder) {
                return ((Reminder) value).getTitle();

            } else if (value instanceof Todo) {
                lastTodo = ((Todo) value).getTitle();

            } else if (value.getClass() == Event.class) {
                lastEvent = ((Event) value).getTitle();
            }
        }

        if (lastEvent != null) {
            return lastEvent;
        } else if (lastTodo != null) {
            return lastTodo;
        }
        return "Nothing yet!";
    }

    public static TreeMap<Long, ? super Event> getPlanner() {
        return planner;
    }
}

