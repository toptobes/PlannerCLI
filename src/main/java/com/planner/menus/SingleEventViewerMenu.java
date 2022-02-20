package com.planner.menus;

import com.planner.DateManager;
import com.planner.Events.Event;
import java.util.Scanner;

public class SingleEventViewerMenu implements Menu {

    Scanner scan = new Scanner(System.in);
    private final Event event;

    public SingleEventViewerMenu(Event event) {
        this.event = event;
    }

    @Override
    public void show() {

        do {
            printEvent(event);

        } while (!scan.nextLine().equalsIgnoreCase("end"));
    }

    public static void printEvent(Event event) {
        System.out.println("----------------------------------------------------------------------------------------");

        printEventBody(event);

        for (int i = event.description().split("\n").length + 5; i < 13; i++) {
            System.out.println();
        }

        System.out.print("""
                *Type "end" at any time to exit
                ----------------------------------------------------------------------------------------
                >\040""");
    }

    private static void printEventBody(Event event) {
        String date;

        DateManager dm = new DateManager();

        String startDate = dm.toFormattedDate(event.startTime());
        String endDate = dm.toFormattedDate(event.endTime());

        if (startDate.equals(endDate)) {
            date = startDate;
        } else {
            date = startDate + " - " + endDate;
        }

        System.out.printf("""
                        %s
                                            
                        %s
                        %s
                                        
                        %s
                        %s
                        """,
                date + " (" + DateManager.getFormat() + ")",
                event.type().getTitleHeader(),
                event.title(),
                event.type().getDescriptionHeader(),
                event.description());
    }
}
