package com.planner;

import com.planner.menus.EventBuilderMenu;
import com.planner.menus.MainMenu;
import com.planner.menus.OptionsMenu;

import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws ParseException {
        Menu.start();
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void start() {
        new MainMenu().show();
        showMenu();
    }

    private static void showMenu() {
        String input;

        do {

            new OptionsMenu().show();
            read(input = scan.nextLine());

        } while (!input.equals("exit"));
    }

    private static void read(String input) {
        switch (input.toLowerCase(Locale.ROOT)) {
            case "view events" -> {}
            case "add event" -> new EventBuilderMenu().show();
            case "remove event" -> {}
            case "settings" -> {}
            case "main menu" -> new MainMenu().show();
        }
    }
}
