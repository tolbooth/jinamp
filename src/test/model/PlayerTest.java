package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.LinkedList;

public class PlayerTest {
    private Track testTrack1;
    private Track testTrack2;
    private Player testPlayer;
    private Player testPlayerCrCp;

    @BeforeEach
    // Initialize data members
    void init() {
        File file = new File("./././data/nicolasJaarDivorce.wav");
        testTrack1 = new Track(file);
        testTrack2 = new Track(file);
        testPlayer = new Player();
    }

    @Test
    void testPlayer() {
        assertNull(testPlayer.getCurrentTrack());
        assertTrue(testPlayer.getQueue().isEmpty());
        assertFalse(testPlayer.getIsPlaying());
        assertEquals(testPlayer.getCurrentPosition(), 0);

        testPlayerCrCp = new Player(testTrack1, 700000);
        assertEquals(testPlayerCrCp.getCurrentTrack(), testTrack1);
        assertTrue(testPlayerCrCp.getQueue().isEmpty());
        assertFalse(testPlayerCrCp.getIsPlaying());
        assertEquals(testPlayerCrCp.getCurrentPosition(), 700000);
    }

    @Test
    void testPlayTrack() {
        try {
            testPlayer.playTrack(testTrack1);
            assertTrue(testPlayer.getIsPlaying());
        } catch (Exception e) {
            System.out.println("Issue with the player constructor");
            fail();
        }
    }

    @Test
    void testPauseResumeTrack() {
        try {
            testPlayer.playTrack(testTrack1);
            assertTrue(testPlayer.getIsPlaying());
            assertFalse(testPlayer.pauseResumeTrack());
            assertTrue(testPlayer.pauseResumeTrack());
        } catch (Exception e) {
            System.out.println("Issue with play/pause!");
            fail();
        }
    }

    @Test
    void testStopTrack() {
        try {
            testPlayer.playTrack(testTrack1);
            assertTrue(testPlayer.getIsPlaying());
            testPlayer.stopTrack();
            assertFalse(testPlayer.getIsPlaying());
        } catch (Exception e) {
            System.out.println("Issue with stopping track");
            fail();
        }
    }

    @Test
    void testEnqueueTrack() {
        testPlayer.enqueueTrack(testTrack1);
        testPlayer.enqueueTrack(testTrack2);
        assertEquals(testPlayer.getTrack(0), testTrack1);
        assertEquals(testPlayer.getTrack(1), testTrack2);
    }

    @Test
    void testDequeueTrack() {
        testEnqueueTrack();
        testPlayer.dequeueTrack();
        assertEquals(testPlayer.getTrack(0), testTrack2);
        testPlayer.dequeueTrack();
        assertTrue(testPlayer.getQueue().isEmpty());
    }

    @Test
    public void testAssignTrack() {
        testPlayer.assignTrack(testTrack1);
        assertNotNull(testPlayer.getCurrentTrack());
    }

    public void getQueue() {
        testPlayer.enqueueTrack(testTrack1);
        testPlayer.enqueueTrack(testTrack2);
        assertEquals(testPlayer.getQueue().getFirst(), testTrack1);
        assertEquals(testPlayer.getQueue().getLast(), testTrack2);
    }

    public void getTrack() {
        testEnqueueTrack();
        assertEquals(testPlayer.getTrack(0), testTrack1);
        assertEquals(testPlayer.getTrack(1), testTrack2);
    }
}
