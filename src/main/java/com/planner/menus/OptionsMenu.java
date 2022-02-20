package com.planner.menus;

import com.planner.Planner;
import com.planner.utility.Color;
import com.planner.utility.DateManager;
import com.planner.utility.MenuPrinter;
import com.planner.utility.Settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class OptionsMenu implements Menu {
    private final Scanner scan = new Scanner(System.in);
    private final Settings settings;

    public OptionsMenu() {
        settings = new Settings();
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
                              
                Upcoming reminder:
                 - %s
                                
                *Not case sensitive
                *Type "end" at any time to leave
                --------------------------------------------------------------------------------------------------
                >\040""", DateManager.getFormat(), Planner.INSTANCE.getNextReminder().title());
    }

    private boolean evaluateResponse() {
        String input = scan.nextLine();

        switch (input.toLowerCase()) {
            case "set color", "color" -> queryAndSetColor();
            case "add event", "scale" -> new EventAdderMenu().show();
            case "remove event", "format" -> new EventRemoverMenu().show();
            case "load planner from file", "load" -> new OptionsMenu().show();
            case "reset planner", "reset" -> new TitleScreen().show();
            case "upcoming reminder", "u" -> new SingleEventViewerMenu(Planner.INSTANCE.getNextReminder()).show();
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
                settings.setProperty(Settings.Properties.COLOR.yamlName(), response);
            } catch (IOException e) {
                MenuPrinter.printErrorScreen("Error saving color '%s' to storage".formatted(response));
            }
        } else {
            MenuPrinter.printErrorScreen("Oops! color '%s' not found".formatted(response));
        }
    }
}
