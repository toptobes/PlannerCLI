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
                "Cancelled addition of event", 15, 5
        );

        switch (response) {
            case "a" -> Planner.addEvent(buildDefaultEvent());
            case "b" -> Planner.addEvent(buildReminder());
            case "c" -> Planner.addEvent(buildTodo());
            case "d" -> Planner.addEvent(buildDailyReminder());
            case "e" -> Planner.addEvent(buildDiaryEntry());

            default -> MenuPrinter.printCancellationScreen("" +
                    "Cancelled addition of event due to invalid input", 15, 5
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
                e) Diary entry
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
                MenuPrinter.errorScreen("Error with adding event to the planner");
                return null;
            }

            return event.build();

        } catch (ParseException ignored) {
            MenuPrinter.errorScreen("Error with parsing date; did you enter the right format?");
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

    private DiaryEntry buildDiaryEntry() {
        return new DiaryEntry(buildDefaultEvent());
    }

    private boolean setStartTime(Event event) throws ParseException {
        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "When does this event start?\n" +
                "The current format is " + DateManager.getFormatter());

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

        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "When does this event end?\n" +
                "The current format is " + DateManager.getFormatter() +
                "\nType \"null\" if it's a one-time event instead of a period of time");

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
        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "What is the title of this event?");

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

        MenuPrinter.printMenuWithCancel("" +
                "Add event:\n" +
                "What is the description of this event?");

        String response = scan.nextLine();

        if (response.equalsIgnoreCase("cancel")) {
            return false;
        }

        event.setDescription(response);
        return true;
    }

    private boolean setTodo(Todo todo) {
        String response;
        while (true) {
            MenuPrinter.printMenuWithCancel("" +
                    "Add event:\n" +
                    "What is the description of this event?");

            response = scan.nextLine();

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
