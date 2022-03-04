package com.planner;

import com.planner.menus.*;
import com.planner.utility.Color;
import com.planner.utility.Config;

import java.util.Scanner;

public class PlannerCLI {
    public static void main(String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print(Color.DEFAULT);
            Planner.savePlanner();
        }));

        Planner.loadPlanner();

        Config.INSTANCE.loadSettings();

        PlannerCLI.start();
    }

    private static final Scanner scan = new Scanner(System.in);

    public static void start() {
        new TitleScreen().show();

        var menu = new MainMenu();

        while (true) {
            menu.show();
            read(scan.nextLine());
        }
    }

    private static void read(String input) {

        switch (input.toLowerCase()) {
            case "view events", "v" -> new EventViewerMenu().show();
            case "add event", "a" -> new EventAdderMenu().show();
            case "remove event", "r" -> new EventRemoverMenu().show();
            case "options", "o" -> new OptionsMenu().show();
            case "main menu", "m" -> new TitleScreen().show();
            case "upcoming reminder", "u" -> new SingleEventViewerMenu(Planner.INSTANCE.getNextReminder()).show();
            case "exit" -> System.exit(0);
        }
    }
}
