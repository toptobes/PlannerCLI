package com.planner.menus;

public class OptionsMenu implements Menu{
    @Override
    public void show() {
        System.out.println("""
                ----------------------------------------------------------------------------------------
                Planner App v0.1.0
                
                "View events" -> View your current events
                "Add event" -> Add an event to your planner
                "Remove event" -> Remove an event from your planner
                
                "Settings" -> Open settings
                "Main Menu" -> Exit to main menu
                "Exit" -> Exit
                
                
                
                
                *Not case sensitive
                ----------------------------------------------------------------------------------------""");
    }
}
