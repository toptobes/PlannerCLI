package com.planner;

import com.planner.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlannerTest {

    Planner planner = Planner.INSTANCE;

    @BeforeEach
    public void clearPlanner() {
        planner.resetPlanner();
    }

    @Test
    void givenNull_whenAddEvent_thenDontThrowException() {
        assertDoesNotThrow(() -> planner.addEvent(null));
    }

    @Test
    void givenEvent_whenAddEvent_thenWork() {
        assertDoesNotThrow(() -> planner.addEvent(Event.getDefaultEvent()));
    }

    @Test
    void givenOutOfBoundsIndex_whenRemoveEventAtIndex_thenDoesntThrowException() {
        assertDoesNotThrow(() -> planner.removeEventAt(Integer.MAX_VALUE));
        assertDoesNotThrow(() -> planner.removeEventAt(-1));
    }

    @Test
    void givenValidIndex_whenRemoveEventAtIndex_thenRemovesEventAtIndex() {
        planner.addEvent(Event.getDefaultEvent());
        planner.removeEventAt(-1);
        assertEquals(0, planner.getEventsList().size());
    }

    @Test
    void givenLargerAmountOfElementsPerPageThanNumElements_whenGetPages_thenReturnsOnePage() {
        planner.addEvent(Event.getDefaultEvent());
        var oneElementPage = new ArrayList<>(List.of(new ArrayList<>(List.of(Event.getDefaultEvent()))));
        assertEquals(oneElementPage, planner.getPages(100));
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