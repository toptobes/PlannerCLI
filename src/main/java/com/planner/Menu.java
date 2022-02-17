package com.planner;

import com.planner.menus.*;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Menu.start();
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void start() {
        new MainMenu().show();

        String input;

        do {
            new OptionsMenu().show();
            read(input = scan.nextLine());

        } while (!input.equals("exit"));
    }

    private static void read(String input) {
        switch (input.toLowerCase()) {
            case "view events", "v" -> new EventsViewerMenu().show();
            case "add event", "a" -> new EventBuilderMenu().show();
            case "remove event", "r" -> new EventsRemoverMenu().show();
            case "options", "o" -> {}
            case "main menu", "m" -> new MainMenu().show();
        }
    }
}
