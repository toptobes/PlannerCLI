package com.planner.menus;

import com.planner.Events.Event;
import com.planner.MenuPrinter;

import java.util.Scanner;

public class SingleEventViewerMenu implements Menu{

    Scanner scan = new Scanner(System.in);
    private final Event event;

    public SingleEventViewerMenu(Event event) {
        this.event = event;
    }

    @Override
    public void show() {

        do {
            MenuPrinter.printEvent(event);

        } while (!scan.nextLine().equalsIgnoreCase("end"));
    }
}
