package com.planner.menus;

import com.planner.DateManager;
import com.planner.Events.*;
import com.planner.MenuPrinter;
import com.planner.Planner;

import java.text.ParseException;
import java.util.Scanner;

public class EventBuilderMenu implements Menu {
    Scanner scan = new Scanner(System.in);

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
            case "a" -> Planner.addEvent(buildDefaultEvent());
            case "b" -> Planner.addEvent(buildReminder());
            case "c" -> Planner.addEvent(buildTodo());
            case "d" -> Planner.addEvent(buildDailyReminder());

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

    private Event buildDefaultEvent() {
        return buildDefaultEvent(true, true);
    }

    private Event buildDefaultEvent(boolean hasEndTime, boolean hasDescription) {
        Event event = new Event();
        try {

            if (!setStartTime(event)
                    || !setEndTime(event, hasEndTime)
                    || !setTitle(event)
                    || !setDescription(event, hasDescription)
            ) {
                MenuPrinter.printErrorScreen("Error with adding event to the planner");
                return null;
            }

            return event.build();

        } catch (ParseException ignored) {
            MenuPrinter.printErrorScreen("Error with parsing date; did you enter the right format?");
            return null;
        }
    }

    private Reminder buildReminder() {
        return new Reminder(buildDefaultEvent(false, true));
    }

    private Todo buildTodo() {
        Todo todo = new Todo(buildDefaultEvent(false, false));

        if (!setTodo(todo)) {
            return null;
        }

        return todo;
    }

    private DailyReminder buildDailyReminder() {
        return new DailyReminder(buildReminder());
    }

    private boolean setStartTime(Event event) throws ParseException {
        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "When does this event start?\n" +
                "The current format is " + DateManager.getFormat());

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return false;
        }

        event.setStartTime(DateManager.toUnixTimestamp(response));
        return true;
    }

    private boolean setEndTime(Event event, boolean hasEndTime) throws ParseException {
        if (!hasEndTime) {
            return true;
        }

        MenuPrinter.printMenuWithCancel("""
                Add event:
                When does this event end?
                The current format is %s
                Type "null" if it's a one-time event instead of a period of time""".formatted(DateManager.getFormat()));

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return false;
        }

        if (response.equalsIgnoreCase("null")) {
            return true;
        }

        event.setEndTime(DateManager.toUnixTimestamp(response));
        return true;
    }

    private boolean setTitle(Event event) throws ParseException {
        MenuPrinter.printMenuWithCancel("""
                Add event:
                What is the title of this event?""");

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return false;
        }

        event.setTitle(response);
        return true;
    }

    private boolean setDescription(Event event, boolean hasDescription) throws ParseException {
        if (!hasDescription) {
            return true;
        }

        String response;
        StringBuilder description = new StringBuilder();

        MenuPrinter.printMenuWithCancel("""
                Add event:
                What is the description of this event?
                You can type up to 7 lines, type end to stop""");

        for (int i = 7; i > 0; i--) {
            response = scan.nextLine();

            if (i > 1) {
                System.out.print("> ");
            }
            if (response.equalsIgnoreCase("cancel")) {
                return false;
            }
            if (response.equalsIgnoreCase("end")) {
                break;
            }
            description.append(response).append("\n");
        }

        event.setDescription(description.toString());
        return true;
    }

    private boolean setTodo(Todo todo) {
        String response;

        MenuPrinter.printMenuWithCancel("""
                Add event:
                What tasks do you need to do? Type "end" to stop""");

        while (true) {
            response = scan.nextLine();

            System.out.print("> ");

            if (response.equalsIgnoreCase("cancel")) {
                return false;
            }
            if (response.equalsIgnoreCase("end")) {
                break;
            }
            todo.addTodo(response);
        }
        return true;
    }
}
