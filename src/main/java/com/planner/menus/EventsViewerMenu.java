package com.planner.menus;

import com.planner.Events.Event;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.util.ArrayList;
import java.util.Scanner;

public class EventsViewerMenu implements Menu {

    Scanner scan = new Scanner(System.in);
    private int numPages;

    @Override
    public void show() {
        makePages();
        displayEvents();
    }

    private int numPrev = 0, numNext = 0, current = 0;

    private void displayEvents() {
        numNext = numPages - 1;

        do {
            MenuPrinter.printListedMenu(pages.get(current), numPrev, numNext);

        } while (evaluateResponse(scan.nextLine()) != 0);
    }

    private int evaluateResponse(String response) {
        response = response.toLowerCase();

        switch (response) {
            case "end" -> {
                return 0;
            }

            case "next" -> {
                if (numNext <= 0) return 1;
                numNext--;
                numPrev++;
                current++;
            }

            case "prev" -> {
                if (numPrev <= 0) return 1;
                numNext++;
                numPrev--;
                current--;
            }
        }
        if (!response.matches("\\d+")) return 1;

        int responseAsInt = Integer.parseInt(response);

        if (0 <= responseAsInt && responseAsInt < pages.get(current).size()) {
            viewEvent(pages.get(current).get(responseAsInt));
        }

        return 1;
    }

    private void viewEvent(Object event) {
        new SingleEventViewerMenu((Event) event).show();
    }

    private final ArrayList<ArrayList<? super Event>> pages = new ArrayList<>();

    private void makePages() {
        ArrayList<? super Event> events = new ArrayList<>(Planner.getPlanner().values());

        if (events.size() == 0) {
            for (int i = 0; i < 38; i++) {
                events.add(Event.getDefaultEvent());
            }
        }

        setNumPages(events.size());

        for (int i = 0; i < numPages; i++) {
            pages.add(new ArrayList<>(
                    events.subList(i * 10, Math.min((i + 1) * 10 + 1, events.size()))
            ));
        }
    }

    public void setNumPages(int numElements) {
        numPages = (int) Math.ceil(1.0 * numElements / 10);
    }
}
