package com.planner;

import com.planner.Events.Event;
import com.planner.Events.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public enum Planner {
    INSTANCE;

    private final ArrayList<Event> planner = new ArrayList<>();

    Planner() {
        for (int i = 0; i < 12; i++) {
            planner.add(Event.getDefaultEvent());
        }
    }

    public void loadPlanner(List<? extends Event> deserializedPlanner) {
        planner.clear();
        planner.addAll(deserializedPlanner);
    }

    public void addEvent(Event event) {
        if (event == null) {
            MenuPrinter.printCancellationScreen("Error: Event is null", 3, 1);
            return;
        }

        planner.add(event);
        Collections.sort(planner);
    }

    public void removeEventAt(int index) {
        if (index < 0 || index > planner.size()) {
            MenuPrinter.printCancellationScreen("Error: Index is out of bounds", 3, 1);
            return;
        }

        planner.remove(index);
        Collections.sort(planner);
    }

    public List<List<Event>> getPages(int eventsPerPage) {
        List<Event> events = new ArrayList<>(planner);

        List<List<Event>> pages = new ArrayList<>();
        int numPages = getNumPages(events, eventsPerPage);

        for (int i = 0; i < numPages; i++)
            pages.add(new ArrayList<>(
                    events.subList(i * eventsPerPage, Math.min((i + 1) * eventsPerPage + 1, events.size()))
            ));

        return pages;
    }

    private int getNumPages(List<?> events, int eventsPerPage) {
        return (int) Math.ceil(1.0 * events.size() / eventsPerPage);
    }

    public String getNextReminder() {
        String lastReminder = null;
        String lastTodo = null;
        String lastEvent = null;

        for (Event e : planner) {
            if (e.type() == Type.REMINDER) lastReminder = e.title();
            if (e.type() == Type.TODO) lastTodo = e.title();
            if (e.type() == Type.EVENT) lastEvent = e.title();

        }

        return Stream.of(lastReminder, lastTodo, lastEvent)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Nothing here yet!");
    }

    public List<Event> getPlanner() {
        return planner;
    }
}