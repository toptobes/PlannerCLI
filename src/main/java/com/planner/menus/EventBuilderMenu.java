package com.planner.menus;

import com.planner.DateManager;
import com.planner.Events.Event;
import com.planner.MenuPrinter;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class EventBuilderMenu implements Menu {
    Scanner scan = new Scanner(System.in);

    @Override
    public void show() {
        buildEventOfTypeX();
    }

    private void buildEventOfTypeX() {
        String response;
        do {
            MenuPrinter.printBuilder("""
                    Add event:
                    What type of event is this?
                    a) Default event
                    b) Reminder
                    c) Todo
                    d) Daily task
                    e) Diary entry
                    """);
            response = scan.nextLine().toLowerCase();
        } while (List.of("a", "b", "c", "d", "e", "cancel").contains(response));

        switch (response) {
            case "a" -> buildDefaultEvent();
            case "b" -> {}
            case "c" -> {}
            case "d" -> {}
            case "e" -> {}
        }
    }

    private void buildDefaultEvent() {
        Event event = new Event();
        try {

            setStartTime(event);
            setEndTime(event);
            setTitle(event);
            setDescription(event);

        } catch (ParseException ignored) {
        }
    }

    private void setStartTime(Event event) throws ParseException {
        MenuPrinter.printBuilder("" +
                "Add event:\n" +
                "When does this event start?\n" +
                "The current format is " + DateManager.getFormat());

        event.setStartTime(DateManager.toUnixTimestamp(scan.nextLine()));
    }

    private void setEndTime(Event event) throws ParseException {
        MenuPrinter.printBuilder("" +
                "Add event:\n" +
                "When does this event end?\n" +
                "The current format is " + DateManager.getFormat() +
                "\nType \"null\" if it's a one-time event instead of a period of time");

        String response = scan.nextLine();
        if (response.equals("null")) event.setEndTime(event.getStartTime());
        event.setEndTime(DateManager.toUnixTimestamp(scan.nextLine()));
    }

    private void setTitle(Event event) throws ParseException {
        MenuPrinter.printBuilder("" +
                "Add event:\n" +
                "What is the title of this event?");

        event.setTitle(scan.nextLine());
    }

    private void setDescription(Event event) throws ParseException {
        MenuPrinter.printBuilder("" +
                "Add event:\n" +
                "What is the description of this event?");

        event.setDescription(scan.nextLine());
    }
}
