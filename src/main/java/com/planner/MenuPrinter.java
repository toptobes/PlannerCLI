package com.planner;

import com.planner.Events.Event;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuPrinter {
    static Scanner scan = new Scanner(System.in);

    public static void printMenuWithCancel(String toPrint) {
        System.out.printf("""
                ----------------------------------------------------------------------------------------
                %s
                """, toPrint);

        for (int i = toPrint.split("\n").length; i < 13; i++) {
            System.out.println();
        }

        System.out.print("""
                *Type "cancel" at any time to exit
                ----------------------------------------------------------------------------------------
                >\040""");
    }

    public static void printErrorScreen(String message) {
        printCancellationScreen(message, 3, 1);
    }

    public static void printCancellationScreen(String cancellationMessage, int seconds, int step) {
        for (int i = seconds; i >= 0; i -= step) {
            System.out.printf("""
                    ----------------------------------------------------------------------------------------
                    %s
                    """, cancellationMessage);

            for (int j = cancellationMessage.split("\n").length; j < 13; j++) {
                System.out.println();
            }

            System.out.printf("""
                    This screen will close in %d seconds
                    ----------------------------------------------------------------------------------------
                    >\040""", i);
        }
    }
}
