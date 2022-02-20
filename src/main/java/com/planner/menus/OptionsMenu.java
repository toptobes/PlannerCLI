package com.planner.menus;

import com.planner.Planner;

public class OptionsMenu implements Menu{
    @Override
    public void show() {
        System.out.printf("""
                ----------------------------------------------------------------------------------------
                Planner App v0.1.0
                
                "View events" -> View your current events
                "Add event" -> Add an event to your planner
                "Remove event" -> Remove an event from your planner
                
                "Options" -> Open the options menu
                "Main Menu" -> Exit to main menu
                "Exit" -> Exit
                
                Upcoming reminder:
                 - %s
                
                *Not case sensitive
                ----------------------------------------------------------------------------------------
                >\040""", Planner.INSTANCE.getNextReminder());
    }
}
