package com.planner;

public class MenuPrinter {
    public static void printMenuWithCancel(String toPrint) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(toPrint);

        for (int i = toPrint.split("\n").length; i < 13; i++) {
            System.out.println();
        }

        System.out.println("*Type \"cancel\" at any time to exit");
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public static void printCancellationScreen(String cancellationMessage, int tenthOfSeconds, int step) {
        for (int i = tenthOfSeconds - 1; i >= 0; i -= step) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(cancellationMessage);

            for (int j = cancellationMessage.split("\n").length; j < 13; j++) {
                System.out.println();
            }

            System.out.printf("This screen will close in %.1f seconds\n", i/10.0);
            System.out.println("----------------------------------------------------------------------------------------");
        }
    }

    public static void errorScreen(String message) {
        printCancellationScreen(message, 15, 5);
    }
}
