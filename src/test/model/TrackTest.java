package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TrackTest {
    File file;

    @BeforeEach
    void init() {
        file = new File("./././data/nicolasJaarDivorce.wav");
    }

    @Test
    // Simple constructor test
    void testTrack() {
        // Relative path
        Track track = new Track(file);
        assertEquals(track.getTrackName(), file.getName());
    }

    @Test
    void accessClipTest(){
        Track track = new Track(file);
        assertNotNull(track.accessClip());
    }
}
