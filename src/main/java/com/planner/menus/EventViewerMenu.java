package com.planner.menus;

import com.planner.event.Event;
import com.planner.Planner;
import com.planner.utility.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class EventViewerMenu implements Menu {
    private static Scanner scan = new Scanner(System.in);

    @Override
    public void show() {
        boolean cancelled = false;

        while (!cancelled) {
            printListedEvents();
            cancelled = evaluateResponse();
        }
    }

    private final List<List<Event>> pages = new ArrayList<>(Planner.INSTANCE.getPages(10));
    private int numPrev = 0;
    private int numNext = 0;
    private int current = 0;

    private void printListedEvents() {
        if (pages.isEmpty()) pages.add(List.of(Event.getDefaultEvent()));

        System.out.println("--------------------------------------------------------------------------------------------------");

        int i = 0;
        for (Event event : pages.get(current)) {
            System.out.println(i + ". " + event);
            i++;
        }

        for (int j = i; j < 12; j++) {
            System.out.println();
        }

        System.out.printf("""
                *You can type any number to view the event
                *Type "end" at any time to leave            < Prev (%02d)            (%02d) Next >
                --------------------------------------------------------------------------------------------------
                >\040""", numPrev, numNext);
    }

    private boolean evaluateResponse() {
        String response = scan.nextLine().toLowerCase();

        switch (response) {
            case "end" -> {
                return true;
            }

            case "next" -> {
                if (numNext <= 0) return false;
                numNext--;
                numPrev++;
                current++;
            }

            case "prev" -> {
                if (numPrev <= 0) return false;
                numNext++;
                numPrev--;
                current--;
            }
        }

        if (!response.matches("\\d{1,3}")) return false;

        int responseAsInt = Integer.parseInt(response);

        if (0 <= responseAsInt && responseAsInt < pages.get(current).size()) {
            viewEvent(pages.get(current).get(responseAsInt));
        }

        return false;
    }

    private void viewEvent(Event event) {
        new SingleEventViewerMenu(event).show();
    }
}
