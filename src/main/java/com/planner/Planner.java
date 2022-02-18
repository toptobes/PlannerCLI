package com.planner;

import com.planner.Events.Event;
import com.planner.Events.Reminder;
import com.planner.Events.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Planner {
    private static final Planner instance = new Planner();

    public static Planner getInstance() {
        return instance;
    }

    private Planner() {
        planner = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            planner.add(Event.getDefaultEvent());
        }
    }

    //---------------------------------------------------------------------------------

    private final ArrayList<Event> planner;

    public static <E extends Event> void loadPlanner(List<E> deserializedPlanner) {
        instance.planner.clear();
        instance.planner.addAll(deserializedPlanner);
    }

    public static <E extends Event> void addEvent(E event) {
        if (event == null) {
            return;
        }

        instance.planner.add(event);
        Collections.sort(instance.planner);
    }

    public static void removeEventAt(int index) {
        if (index < 0 || index > instance.planner.size()) {
            return;
        }

        instance.planner.remove(index);
        Collections.sort(instance.planner);
    }

    public static ArrayList<ArrayList<Event>> getPages(int eventsPerPage) {
        ArrayList<Event> events = new ArrayList<>(instance.planner);

        ArrayList<ArrayList<Event>> pages = new ArrayList<>();
        int numPages = getNumPages(events, eventsPerPage);

        for (int i = 0; i < numPages; i++)
            pages.add(new ArrayList<>(
                    events.subList(i * eventsPerPage, Math.min((i + 1) * eventsPerPage + 1, events.size()))
            ));

        return pages;
    }

    public static int getNumPages(List<?> events, int eventsPerPage) {
        return (int) Math.ceil(1.0 * events.size() / eventsPerPage);
    }

    public static String getNextReminder() {
        String lastTodo = null, lastEvent = null;

        for (Event value : instance.planner) {

            if (value instanceof Reminder) {
                return value.toString();

            } else if (value instanceof Todo) {
                lastTodo = value.toString();

            } else if (value.getClass() == Event.class) {
                lastEvent = value.toString();
            }
        }

        if (lastEvent != null) {
            return lastEvent;

        } else if (lastTodo != null) {
            return lastTodo;
        }
        return "Nothing yet!";
    }

    public static ArrayList<Event> getPlanner() {
        return instance.planner;
    }
}

