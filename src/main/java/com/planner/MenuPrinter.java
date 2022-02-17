package com.planner;

import com.planner.Events.Event;

import java.text.ParseException;
import java.util.List;

public class MenuPrinter {
    public static void printMenuWithCancel(String toPrint) {
        System.out.printf("""
                    ----------------------------------------------------------------------------------------
                    %s
                    """, toPrint);

        for (int i = toPrint.split("\n").length; i < 13; i++) {
            System.out.println();
        }

        System.out.print("""
                *Type "cancel" at any time to exit
                ----------------------------------------------------------------------------------------
                >\040""");
    }

    public static void printErrorScreen(String message) {
        printCancellationScreen(message, 3, 1);
    }

    public static void printCancellationScreen(String cancellationMessage, int seconds, int step) {
        for (int i = seconds; i >= 0; i -= step) {
            System.out.printf("""
                    ----------------------------------------------------------------------------------------
                    %s
                    """, cancellationMessage);

            for (int j = cancellationMessage.split("\n").length; j < 13; j++) {
                System.out.println();
            }

            System.out.printf("""
                    This screen will close in %d seconds
                    ----------------------------------------------------------------------------------------
                    >\040""", i);
        }
    }

    public static void printListedMenu(List<? super Event> events, int numPrevious, int numNext) {
        System.out.println("----------------------------------------------------------------------------------------");

        int i = 0;
        for (Object event : events) {
            if (i >= 10) break;

            System.out.println(i + ". " + ((Event) event).toString(event.getClass().getSimpleName().replace("Daily", "")));
            i++;
        }

        for (int j = i; j < 12; j++) {
            System.out.println();
        }

        System.out.printf("""
                *You can type any number to view the event
                "*Type "end" at any time to leave            < Prev (%02d)            (%02d) Next >
                ----------------------------------------------------------------------------------------
                >\040""", numPrevious, numNext);
    }

    public static void printEvent(Event event) {
        System.out.println("----------------------------------------------------------------------------------------");

        printEventBody(event);

        for (int i = event.getDescription().split("\n").length + 5; i < 13; i++) {
            System.out.println();
        }

        System.out.print("""
                *Type "end" at any time to exit
                ----------------------------------------------------------------------------------------
                >\040""");
    }

    private static void printEventBody(Event event) {
        String date = "Error parsing date";

        try {
            String startDate = DateManager.toFormattedDate(event.getStartTime());
            String endDate = DateManager.toFormattedDate(event.getStartTime());

            if (startDate.equals(endDate)) {
                date = startDate;
            } else {
                date = startDate + " - " + endDate;
            }

        } catch (ParseException ignored) {
        }

        System.out.printf("""
                    %s
                    
                    %s
                    %s
                                    
                    %s
                    %s
                    """,
                date + " (" + DateManager.getFormatter() + ")",
                event.getTitleHeader(),
                event.getTitle(),
                event.getDescriptionHeader(),
                event.getDescription());
    }
}
