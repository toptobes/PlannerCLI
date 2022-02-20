package com.planner.menus;

import com.planner.Planner;
import com.planner.event.Event;
import com.planner.event.EventType;
import com.planner.utility.DateManager;
import com.planner.utility.MenuPrinter;

import java.text.ParseException;
import java.util.Scanner;

public class EventAdderMenu implements Menu {

    Planner planner = Planner.INSTANCE;
    Scanner scan = new Scanner(System.in);
    DateManager dm = new DateManager();

    @Override
    public void show() {
        try {
            buildEventOfTypeX();

        } catch (ParseException ignored) {
            MenuPrinter.printErrorScreen("Error with parsing date; did you enter the right format?");
        }
    }

    private void buildEventOfTypeX() throws ParseException {
        String response = queryTypeOfEvent();
        Event event = null;

        if (response.equals("cancel")) MenuPrinter.printCancellationScreen(
                "Cancelled addition of event", 3, 1
        );

        switch (response) {
            case "a" -> event = buildEvent(EventType.EVENT);
            case "b" -> event = buildEvent(EventType.REMINDER);
            case "c" -> event = buildEvent(EventType.TODO);
            case "d" -> event = buildEvent(EventType.DAILY_REMINDER);

            default -> MenuPrinter.printCancellationScreen(
                    "Cancelled addition of event due to invalid input", 3, 1
            );
        }

        if (event != null) planner.addEvent(event);
    }

    private String queryTypeOfEvent() {
        printMenuWithCancel("""
                Add event:
                What type of event is this?
                a) Default event
                b) Reminder
                c) Todo
                d) Daily reminder
                """);
        return scan.nextLine();
    }

    private Event buildEvent(EventType type) throws ParseException {
        Long startTime;
        Long endTime;
        String title;
        String description;

        if ((startTime = getStartTime()) == null
                || (endTime = getEndTime(type, startTime)) == null
                || (title = getTitle(type)) == null
                || (description = getDescription(type)) == null
        ) {
            MenuPrinter.printCancellationScreen("Cancelled event addition",3 , 1);
            return null;
        }

        return new Event(type, title, description, startTime, endTime);

    }

    private Long getStartTime() throws ParseException {
        printMenuWithCancel("" +
                "Add event:\n" +
                "When does this event start?\n" +
                "The current format is " + DateManager.getFormat());

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return null;
        }

        return dm.toUnixTimestamp(response);
    }

    private Long getEndTime(EventType type, Long startTime) throws ParseException {
        if (!type.hasEndTime()) {
            return startTime;
        }

        printMenuWithCancel("""
                Add event:
                When does this event end?
                The current format is %s
                Type "same" if it's a one-time event instead of a period of time""".formatted(DateManager.getFormat()));

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return null;
        }

        if (response.equalsIgnoreCase("same")) {
            return startTime;
        }

        return dm.toUnixTimestamp(response);
    }

    private String getTitle(EventType type) {
        if (!type.hasTitle()) return "";

        printMenuWithCancel("""
                Add event:
                What is the title of this event?""");

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return null;
        }

        return response;
    }

    private String getDescription(EventType type) {
        if (type == EventType.TODO) return getTodo();
        return getDescription();
    }

    private String getDescription() {
        printMenuWithCancel("""
                Add event:
                What is the description of this event?
                You can type up to 7 lines, type end to stop""");

        return queryDescription();
    }

    private String getTodo() {
        printMenuWithCancel("""
                Add event:
                What tasks do you need to do? Type "end" to stop""");

        return queryDescription();
    }

    private String queryDescription() {
        StringBuilder description = new StringBuilder();
        String response;

        for (int i = 7; i > 0; i--) {
            response = scan.nextLine();

            if (i > 1) {
                System.out.print("> ");
            }
            if (response.equalsIgnoreCase("cancel")) {
                return null;
            }
            if (response.equalsIgnoreCase("end")) {
                break;
            }
            description.append(response).append("\n");
        }

        return description.toString();
    }

    private void printMenuWithCancel(String toPrint) {
        System.out.printf("""
                --------------------------------------------------------------------------------------------------
                %s
                """, toPrint);

        for (int i = toPrint.split("\n").length; i < 13; i++) {
            System.out.println();
        }

        System.out.print("""
                *Type "cancel" at any time to exit
                --------------------------------------------------------------------------------------------------
                >\040""");
    }
}
