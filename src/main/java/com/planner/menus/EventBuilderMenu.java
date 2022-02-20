package com.planner.menus;

import com.planner.DateManager;
import com.planner.Events.*;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.text.ParseException;
import java.util.Scanner;

public class EventBuilderMenu implements Menu {

    Planner planner = Planner.INSTANCE;
    Scanner scan = new Scanner(System.in);
    DateManager dm = new DateManager();

    @Override
    public void show() {
        buildEventOfTypeX();
    }

    private void buildEventOfTypeX() {
        String response = queryTypeOfEvent();

        if (response.equals("cancel")) MenuPrinter.printCancellationScreen(
                "Cancelled addition of event", 3, 1
        );

        switch (response) {
            case "a" -> planner.addEvent(buildEvent(Type.EVENT));
            case "b" -> planner.addEvent(buildEvent(Type.REMINDER));
            case "c" -> planner.addEvent(buildEvent(Type.TODO));
            case "d" -> planner.addEvent(buildEvent(Type.DAILY_REMINDER));

            default -> MenuPrinter.printCancellationScreen("" +
                    "Cancelled addition of event due to invalid input", 3, 1
            );
        }
    }

    private String queryTypeOfEvent() {
        MenuPrinter.printMenuWithCancel("""
                Add event:
                What type of event is this?
                a) Default event
                b) Reminder
                c) Todo
                d) Daily reminder
                """);
        return scan.nextLine();
    }

    private Event buildEvent(Type type) {
        Long startTime;
        Long endTime;
        String title;
        String description;

        try {

            if ((startTime = getStartTime()) == null
                    || (endTime = getEndTime(type, startTime)) == null
                    || (title = getTitle()) == null
                    || (description = getDescription(type)) == null
            ) {
                MenuPrinter.printErrorScreen("Error with adding event to the planner");
                return null;
            }

            return new Event(type, title, description, startTime, endTime);

        } catch (ParseException ignored) {
            MenuPrinter.printErrorScreen("Error with parsing date; did you enter the right format?");
            return null;
        }
    }

    private Long getStartTime() throws ParseException {
        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "When does this event start?\n" +
                "The current format is " + DateManager.getFormat());

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return null;
        }

        return dm.toUnixTimestamp(response);
    }

    private Long getEndTime(Type type, Long startTime) throws ParseException {
        if (!type.hasEndTime()) {
            return startTime;
        }

        MenuPrinter.printMenuWithCancel("""
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

    private String getTitle() {
        MenuPrinter.printMenuWithCancel("""
                Add event:
                What is the title of this event?""");

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return null;
        }

        return response;
    }

    private String getDescription(Type type) {
        if (type == Type.TODO) return getTodo();
        return getDescription();
    }

    private String getDescription() {
        MenuPrinter.printMenuWithCancel("""
                Add event:
                What is the description of this event?
                You can type up to 7 lines, type end to stop""");

        return queryDescription();
    }

    private String getTodo() {
        MenuPrinter.printMenuWithCancel("""
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
}
