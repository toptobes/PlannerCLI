package com.planner.menus;

import com.planner.Events.Event;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.util.ArrayList;

public class EventsRemoverMenu implements Menu {
    @Override
    public void show() {
        while (evaluateResponse(MenuPrinter.printListedEvents(Planner.getPlanner())) != 0) ;
    }

    @SuppressWarnings("unchecked")
    private int evaluateResponse(Object[] arr) {
        var response = (String) arr[0];
        var pages = (ArrayList<ArrayList<Event>>) arr[1];
        var current = (Integer) arr[2];

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
