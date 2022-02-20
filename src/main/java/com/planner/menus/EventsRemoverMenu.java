package com.planner.menus;

import com.planner.Events.Event;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class EventsRemoverMenu implements Menu {
    private static final Scanner scan = new Scanner(System.in);

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
        System.out.println("----------------------------------------------------------------------------------------");

        int i = 0;
        for (Event event : pages.get(current)) {
            System.out.println(i + ". " + event);
            i++;
        }

        for (int j = i; j < 12; j++) {
            System.out.println();
        }

        System.out.printf("""
                *You can type any number to remove the event
                "*Type "end" at any time to leave            < Prev (%02d)            (%02d) Next >
                ----------------------------------------------------------------------------------------
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

        if (!response.matches("\\d+")) return false;

        int responseAsInt = Integer.parseInt(response);

        if (responseAsInt >= 0 && responseAsInt < pages.get(current).size()) {
            Planner.INSTANCE.removeEventAt(responseAsInt);
            new EventsRemoverMenu().show();
        }

        return false;
    }
}
