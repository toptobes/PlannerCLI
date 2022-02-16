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
        String response;
        MenuPrinter.printMenuWithCancel("""
                    Add event:
                    What type of event is this?
                    a) Default event
                    b) Reminder
                    c) Todo
                    d) Daily reminder
                    e) Diary entry
                    """);
        response = scan.nextLine();
        if (response.equals("cancel")) MenuPrinter.printCancellationScreen(
                "Cancelled addition of event", 15, 5
        );

        switch (response) {
            case "a" -> Planner.addEvent(buildDefaultEvent(true, true));
            case "b" -> Planner.addEvent(buildReminder());
            case "c" -> Planner.addEvent(buildTodo());
            case "d" -> Planner.addEvent(buildDailyReminder());
            case "e" -> Planner.addEvent(buildDiaryEntry());
            default -> MenuPrinter.printCancellationScreen(
                    "Cancelled addition of event due to invalid input", 15, 5
            );
        }
    }

    private Event buildDefaultEvent(boolean hasEndTime, boolean hasDescription) {
            var eventBuilder = new Event.Builder();
        try {
            setStartTime(eventBuilder);
            setEndTime(eventBuilder, hasEndTime);
            setTitle(eventBuilder);
            setDescription(eventBuilder, hasDescription);
            return eventBuilder.build();

        } catch (ParseException ignored) {
            return null;
        }
    }

    private Reminder buildReminder() {
        return new Reminder(buildDefaultEvent(false, true));
    }

    private Todo buildTodo() {
        Todo todo = new Todo(buildDefaultEvent(false, false));
        setTodo(todo);
        return todo;
    }

    private DailyReminder buildDailyReminder() {
        return new DailyReminder(buildReminder());
    }

    private DiaryEntry buildDiaryEntry() {
        return new DiaryEntry(buildDiaryEntry());
    }

    private void setStartTime(Event.Builder eventBuilder) throws ParseException {
        MenuPrinter.printMenuWithCancel("" +
                                        "Add event:\n" +
                                        "When does this event start?\n" +
                                        "The current format is " + DateManager.getFormatter());

        eventBuilder.setStartTime(DateManager.toUnixTimestamp(scan.nextLine()));
    }

    private void setEndTime(Event.Builder eventBuilder, boolean hasEndTime) throws ParseException {
        if (!hasEndTime) {
            return;
        }

        MenuPrinter.printMenuWithCancel("" +
                                        "Add event:\n" +
                                        "When does this event end?\n" +
                                        "The current format is " + DateManager.getFormatter() +
                                        "\nType \"null\" if it's a one-time event instead of a period of time");

        String response = scan.nextLine();
        if (response.equalsIgnoreCase("null")) return;
        eventBuilder.setEndTime(DateManager.toUnixTimestamp(scan.nextLine()));
    }

    private void setTitle(Event.Builder eventBuilder) throws ParseException {
        MenuPrinter.printMenuWithCancel("" +
                                        "Add event:\n" +
                                        "What is the title of this event?");

        eventBuilder.setTitle(scan.nextLine());
    }

    private void setDescription(Event.Builder eventBuilder, boolean hasDescription) throws ParseException {
        if (!hasDescription) {
            return;
        }

        MenuPrinter.printMenuWithCancel("" +
                                        "Add event:\n" +
                                        "What is the description of this event?");

        eventBuilder.setDescription(scan.nextLine());
    }

    private void setTodo(Todo todo) {
        String response;
        while (true) {
            MenuPrinter.printMenuWithCancel("" +
                                            "Add event:\n" +
                                            "What is the description of this event?");

            response = scan.nextLine();
            if (response.equalsIgnoreCase("end")) {
                break;
            }
            todo.addTodo(response);
        }
    }
}
