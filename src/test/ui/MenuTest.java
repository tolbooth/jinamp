package ui;

import model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.display.Menu;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    Menu testMenu;

    @BeforeEach
    void init() {
        testMenu = new Menu();
    }

    @Test
    // Simple test for constructor, only testable thing is if player remains null
    void menuTest() {
        assertNotNull(testMenu.getPlayer());
    }

    @Test
    void displayListTest() {
       // This method is best tested visually
        testMenu.displayList();
    }

    @Test
    void displayQueueTest() {
        assertEquals(testMenu.displayQueue(), "");
        testMenu.addTrack(1);
        assertTrue(testMenu.displayQueue().contains("nicolasJaar"));
    }

    @Test
    void startPlayTest() {
        // Confirm that playing from queue doesn't work without a queue to play from
        assertFalse(testMenu.startPlay());
        testMenu.addTrack(1);
        // Now try after having added to queue
        assertTrue(testMenu.startPlay());
        // THe only thing to test here is that it does not throw an exception
        testMenu.startPlay(1);
    }
    // No need for tests for playPause, stopPlay, as they are just containers for
    // methods already tested in player.
}
