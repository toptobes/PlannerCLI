package com.planner;

import com.planner.Events.Event;
import com.planner.Events.Reminder;
import com.planner.Events.Todo;

import java.util.ArrayList;
import java.util.TreeMap;

public class Planner {
    private static final Planner instance = new Planner();

    private Planner() {
        planner = new TreeMap<>();
    }

    public static Planner getInstance() {
        return instance;
    }

    private TreeMap<Long, ? super Event> planner;

    public static void loadPlanner(TreeMap<Long, ? super Event> deserializedPlanner) {
        instance.planner = deserializedPlanner;
    }

    public static <E extends Event> void addEvent(E event) {
        if (event == null) {
            return;
        }

        instance.planner.put(event.getStartTime(), event);
    }

    public static ArrayList<ArrayList<? super Event>> getPages(int eventsPerPage) {
        ArrayList<? super Event> events = new ArrayList<>(instance.planner.values());

        if (events.size() == 0) {
            for (int i = 0; i < 38; i++) {
                events.add(Event.getDefaultEvent());
            }
        }

        ArrayList<ArrayList<? super Event>> pages = new ArrayList<>();
        int numPages = getNumPages(eventsPerPage);

        for (int i = 0; i < numPages; i++)
            pages.add(new ArrayList<>(
                    events.subList(i * eventsPerPage, Math.min((i + 1) * eventsPerPage + 1, events.size()))
            ));

        return pages;
    }

    public static int getNumPages(int eventsPerPage) {
        return (int) Math.ceil(1.0 * instance.planner.size() / eventsPerPage);
    }

    public static <E extends Event> String getNextReminder() {
        String lastTodo = null, lastEvent = null;

        for (Object value : instance.planner.values()) {

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
        return instance.planner;
    }
}

