package com.planner;

import com.planner.event.Event;
import com.planner.event.EventType;
import com.planner.utility.MenuPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public enum Planner {
    INSTANCE;

    private ArrayList<Event> eventsList = new ArrayList<>();

    public void addEvent(Event event) {
        if (event == null) {
            MenuPrinter.printErrorScreen("Oops! Event is null");
            return;
        }

        eventsList.add(event);
        Collections.sort(eventsList);
    }

    public void removeEventAt(int index) {
        if (index < 0 || index >= eventsList.size()) {
            MenuPrinter.printErrorScreen("Oops! Index is out of bounds");
            return;
        }

        eventsList.remove(index);
        Collections.sort(eventsList);
    }

    public List<List<Event>> getPages(int eventsPerPage) {
        List<Event> events = new ArrayList<>(eventsList);

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

    public Event getNextReminder() {
        Event lastReminder = null;
        Event lastTodo = null;
        Event lastEvent = null;

        for (Event e : eventsList) {
            if (e.type() == EventType.REMINDER) lastReminder = e;
            if (e.type() == EventType.TODO) lastTodo = e;
            if (e.type() == EventType.EVENT) lastEvent = e;
        }

        return Stream.of(lastReminder, lastTodo, lastEvent)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseGet(Event::getDefaultEvent);
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public Event getEventAt(int index) {
        return eventsList.get(index);
    }

    public int size() {
        return eventsList.size();
    }

    public static void savePlanner() {
        savePlannerToFile(null);
    }

    public static void loadPlanner() {
        loadPlannerFromFile(null);
    }

    @SuppressWarnings("unchecked")
    public static void loadPlannerFromFile(String filePath) {
        filePath = (filePath == null) ? "src/main/resources/planner-save.ser" : filePath;

        try (var ois = new ObjectInputStream(new FileInputStream(filePath))) {
            INSTANCE.eventsList = (ArrayList<Event>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            MenuPrinter.printErrorScreen("Oops! Couldn't load the planner... rip");
        }
    }

    public static void savePlannerToFile(String filePath) {
        filePath = (filePath == null) ? "src/main/resources/planner-save.ser" : filePath;

        try (var out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(INSTANCE.eventsList);
            System.out.println("saving!");

        } catch (IOException e) {
            MenuPrinter.printErrorScreen("Oops! Couldn't save the planner... rip");
        }
    }

    public void resetPlanner() {
        eventsList = new ArrayList<>();
    }
}