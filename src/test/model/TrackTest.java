package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
