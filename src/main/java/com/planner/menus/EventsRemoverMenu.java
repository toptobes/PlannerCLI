package com.planner.menus;

import com.planner.Events.Event;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class EventsRemoverMenu implements Menu{
    Scanner scan = new Scanner(System.in);

    private final ArrayList<ArrayList<Event>> pages = Planner.getPages(10);
    private final int numPages = Planner.getNumPages(pages.stream().flatMap(Collection::stream).toList(), 10);
    private int numPrev = 0, numNext = 0, current = 0;

    @Override
    public void show() {
        numNext = numPages - 1;

        do {
            MenuPrinter.printListedMenu(pages.get(current), numPrev, numNext);

        } while (evaluateResponse(scan.nextLine()) != 0);
    }

    @SuppressWarnings("DuplicatedCode")
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
            Planner.removeEventAt(responseAsInt);
            new EventsRemoverMenu().show();
            return 0;
        }

        return 1;
    }
}
