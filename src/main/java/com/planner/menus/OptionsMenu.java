package com.planner.menus;

import com.planner.Planner;
import com.planner.utility.Color;
import com.planner.utility.Config;
import com.planner.utility.DateManager;
import com.planner.utility.MenuPrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class OptionsMenu implements Menu {
    private final Scanner scan = new Scanner(System.in);
    private final Config settings;

    public OptionsMenu() {
        settings = Config.INSTANCE;
    }

    @Override
    public void show() {
        boolean cancelled = false;

        while (!cancelled) {
            printMenu();
            cancelled = evaluateResponse();
        }
    }

    public void printMenu() {
        System.out.printf("""
                --------------------------------------------------------------------------------------------------
                Planner App v1.0.0 options:
                                
                "Change color" -> Change the color of your planner
                "Change scale" -> Make the menu bigger or smaller
                "Change time format" -> Changer your time format from '%s'
                                
                "Load planner from file" -> Replace your planner from one from another file
                "Reset planner" -> Completely wipe your planner (Cannot be undone)
                              
                Hotel:
                 - Trivago
                                
                *Not case sensitive
                *Type "end" at any time to leave
                --------------------------------------------------------------------------------------------------
                >\040""", DateManager.getFormat());
    }

    private boolean evaluateResponse() {
        String input = scan.nextLine();

        switch (input.toLowerCase()) {
            case "set color", "color" -> queryAndSetColor();
            case "change scale", "scale" -> {}
            case "change time format", "format" -> queryAndSetFormat();
            case "load planner from file", "load" -> {}
            case "reset planner", "reset" -> Planner.INSTANCE.getEventsList().clear();
            case "end" -> {
                return true;
            }
        }

        return false;
    }

    private void queryAndSetColor() {
        var colorsMap = new HashMap<>(Color.getColors());

        String colors = colorsMap.keySet()
                .toString()
                .replaceAll("[\\[|\\]]", "");

        System.out.printf("""
                --------------------------------------------------------------------------------------------------
                Planner App v1.0.0 options:
                                
                What color do you want to choose?
                %s
                                
                                
                                
                                
                                
                                
                                
                                
                *Not case sensitive
                *Type "cancel" at any time to cancel
                --------------------------------------------------------------------------------------------------
                >\040""", colors);

        String response = scan.nextLine().toUpperCase();

        if ("CANCEL".equals(response)) {
            return;
        }

        if (colorsMap.containsKey(response)) {
            Color.setDefaultColor(colorsMap.get(response));

            try {
                settings.setProperty(Config.Keys.COLOR.yamlName(), response);
            } catch (IOException e) {
                MenuPrinter.printErrorScreen("Error saving color '%s' to storage".formatted(response));
            }
        } else {
            MenuPrinter.printErrorScreen("Oops! color '%s' not found".formatted(response));
        }
    }

    private void queryAndSetFormat() {
        System.out.printf("""
                --------------------------------------------------------------------------------------------------
                Planner App v1.0.0 options:
                                
                What date & time format would you like to choose? Current one is:
                %s
                                
                                
                                
                                
                                
                                
                                
                                
                *Case sensitive
                *Type "cancel" at any time to cancel
                --------------------------------------------------------------------------------------------------
                >\040""", DateManager.getFormat());

        String response = scan.nextLine();

        if ("cancel".equalsIgnoreCase(response)) {
            return;
        }

        try {
            DateManager.setFormat(response);
            settings.setProperty(Config.Keys.FORMAT.yamlName(), response);

        } catch (IOException | IllegalArgumentException e) {
            MenuPrinter.printErrorScreen("Oops! %s appears to be an invalid format".formatted(response));
        }
    }
}
