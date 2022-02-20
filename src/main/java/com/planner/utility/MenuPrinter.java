package com.planner.utility;

import java.util.Scanner;

public class MenuPrinter {
    static Scanner scan = new Scanner(System.in);

    private MenuPrinter() {
        throw new IllegalStateException("Utility class");
    }

    public static void printErrorScreen(String message) {
        printCancellationScreen(message, 3, 1);
    }

    public static void printCancellationScreen(String cancellationMessage, double seconds, double step) {
        for (double i = seconds; i >= 0; i -= step) {
            System.out.printf("""
                    --------------------------------------------------------------------------------------------------
                    %s
                    """, cancellationMessage);

            for (int j = cancellationMessage.split("\n").length; j < 13; j++) {
                System.out.println();
            }

            System.out.printf("""
                    This screen closes in %f seconds
                    --------------------------------------------------------------------------------------------------
                    """, i);

            try {
                Thread.sleep((long) (step * 1000L));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
