package com.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PlannerTest {

    Planner planner = Planner.INSTANCE;

    @Test
    void givenNull_whenAddEvent_thenDontThrowException() {
        assertDoesNotThrow(() -> planner.addEvent(null));
        assertDoesNotThrow(() -> planner.removeEventAt(-1));
    }

    @Test
    void givenMaxIntegerValue_whenRemoveEventAtIndex_thenDoesntThrowException() {
        assertDoesNotThrow(() -> planner.removeEventAt(Integer.MAX_VALUE));
    }

    @Test
    void getPages() {
    }

    @Test
    void getNextReminder() {
    }

    @Test
    void getEventsList() {
    }

    @Test
    void savePlanner() {
    }

    @Test
    void loadPlanner() {
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}