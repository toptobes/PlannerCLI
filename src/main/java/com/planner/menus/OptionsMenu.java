package com.planner.menus;

import com.planner.Planner;
import com.planner.utility.DateManager;

public class OptionsMenu implements Menu {
    @Override
    public void show() {
        System.out.printf("""
                --------------------------------------------------------------------------------------------------
                Planner App v1.0.0
                                
                "Set color" -> Change the color of your planner
                "Change scale" -> Make the menu bigger or smaller
                "Change time format" -> Changer your time format from %s
                                
                "Load planner from file" -> Replace your planner from one from another file
                "Reset planner" -> Completely wipe your planner (Cannot be undone)
                              
                Upcoming reminder:
                 - %s
                                
                *Not case sensitive
                *Type "end" at any time to leave
                --------------------------------------------------------------------------------------------------
                >\040""", DateManager.getFormat(), Planner.INSTANCE.getNextReminder().title());
    }
}
