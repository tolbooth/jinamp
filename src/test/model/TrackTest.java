package model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TrackTest {

    @Test
    // Simple constructor test
    void testTrack() {
        File file = new File("./././data/nicolasJaarDivorce.wav ");
        Track track = new Track(file);
        assertEquals(track.getTrackName(), file.getName());
    }
}
