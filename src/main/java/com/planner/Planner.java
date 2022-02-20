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

    private ArrayList<Event> planner = new ArrayList<>();

    public void addEvent(Event event) {
        if (event == null) {
            MenuPrinter.printErrorScreen("Oops! Event is null");
            return;
        }

        planner.add(event);
        Collections.sort(planner);
    }

    public void removeEventAt(int index) {
        if (index < 0 || index >= planner.size()) {
            MenuPrinter.printErrorScreen("Oops! Index is out of bounds");
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
            if (e.type() == EventType.REMINDER) lastReminder = e.title();
            if (e.type() == EventType.TODO) lastTodo = e.title();
            if (e.type() == EventType.EVENT) lastEvent = e.title();

        }

        return Stream.of(lastReminder, lastTodo, lastEvent)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Nothing here yet!");
    }

    public List<Event> getPlanner() {
        return planner;
    }

    public void savePlanner() {
        try (var out = new ObjectOutputStream(new FileOutputStream("src/main/resources/planner-save.txt"))) {
            out.writeObject(planner);

        } catch (IOException e) {
            MenuPrinter.printErrorScreen("Oops! Couldn't save the planner... rip");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadPlanner() {
        try (var ois = new ObjectInputStream(new FileInputStream("src/main/resources/planner-save.txt"))) {
            planner = (ArrayList<Event>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            MenuPrinter.printErrorScreen("Oops! Couldn't load the planner... rip");
        }
    }
}