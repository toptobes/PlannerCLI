package com.planner;

import com.planner.Events.Event;

import java.text.ParseException;
import java.util.TreeMap;

public class Planner {
    static TreeMap<Long, ? super Event> planner = new TreeMap<>();

    public static <E extends Event> void addEvent(E event) {
        if (event == null) {
            return;
        }

        planner.put(event.getStartTime(), event);
    }
}
