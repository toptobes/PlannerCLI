package com.planner;

import com.planner.Events.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuPrinter {
    static Scanner scan = new Scanner(System.in);

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

    public static <E extends Event> Object[] printListedEvents(List<E> events) {
        return printListedEvents(events, true);
    }

    static ArrayList<ArrayList<Event>> pages = Planner.getPages(10);
    static AtomicInteger numPrev = new AtomicInteger(0);
    static AtomicInteger numNext = new AtomicInteger(0);
    static AtomicInteger current = new AtomicInteger(0);

    public static <E extends Event> String printListedEvents(List<E> events, boolean resetPages) {
        if (resetPages) resetPages();

        System.out.println("----------------------------------------------------------------------------------------");

        int i = 0;
        for (Event event : pages.get(current.get())) {
            System.out.println(i + ". " + event);
            i++;
        }

        for (int j = i; j < 12; j++) {
            System.out.println();
        }

        System.out.printf("""
                *You can type any number to view the event
                "*Type "end" at any time to leave            < Prev (%02d)            (%02d) Next >
                ----------------------------------------------------------------------------------------
                >\040""", numPrev.get(), numNext.get());

        return evaluateResponse(scan.nextLine(), events);
    }

    private static void resetPages() {
        pages = Planner.getPages(10);
        numPrev.set(0);
        numNext.set(0);
        current.set(0);
    }

    private static <E extends Event> String evaluateResponse(String response, List<E> events) {
        response = response.toLowerCase();

        switch (response) {
            case "end" -> {
                return null;
            }

            case "next" -> {
                if (numNext.get() <= 0) printListedEvents(events, false);
                numNext.decrementAndGet();
                numPrev.incrementAndGet();
                current.incrementAndGet();
            }

            case "prev" -> {
                if (numPrev.get() <= 0) printListedEvents(events, false);
                numNext.incrementAndGet();
                numPrev.decrementAndGet();
                current.decrementAndGet();
            }
        }

        return response
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
                date + " (" + DateManager.getFormat() + ")",
                event.getTitleHeader(),
                event.getTitle(),
                event.getDescriptionHeader(),
                event.getDescription());
    }
}
