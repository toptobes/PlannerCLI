package com.planner;

public class MenuPrinter {
    public static void printBuilder(String toPrint) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(toPrint);

        for (int i = toPrint.split("\n").length; i < 13; i++) {
            System.out.println();
        }

        System.out.println("*Type \"cancel\" at any time to exit");
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
