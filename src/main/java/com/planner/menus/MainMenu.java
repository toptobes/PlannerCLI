package com.planner.menus;

import java.util.Scanner;

public class MainMenu implements Menu {
    @Override
    public void show() {
        System.out.print("""
                --------------------------------------------------------------------------------------------------
                
                
                 _____  _
                |  __ \\| |                           \s
                | |__) | | __ _ _ __  _ __   ___ _ __\s
                |  ___/| |/ _` | '_ \\| '_ \\ / _ \\ '__|
                | |    | | (_| | | | | | | |  __/ |  \s
                |_|    |_|\\__,_|_| |_|_| |_|\\___|_|  \s v1.0.1
                                                     \s
                
                 - Type anything to start
                 
                
                --------------------------------------------------------------------------------------------------
                >\040""");
        new Scanner(System.in).next();
    }
}
