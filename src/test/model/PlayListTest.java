package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayListTest {


    @Test
    void testPlayList() {
        PlayList pl1 = new PlayList();

        PlayList pl2 = new PlayList("grindcore", "bubbly", "hymns", "gerogerigegege");

        assertEquals(pl1.getTags().length, 1);
        assertEquals(pl1.getTags()[0], "untagged");
        assertEquals(pl2.getTags().length, 4);
        assertEquals(pl2.getTags()[0], "grindcore");
        assertEquals(pl2.getTags()[1], "bubbly");
        assertEquals(pl2.getTags()[2], "hymns");
        assertEquals(pl2.getTags()[3], "gerogerigegege");
    }

   // Add method being tested elsewhere

    @Test
    void testToJSON() {
        // Functionality of this is tested in persistence test suites
        assertNotNull(new PlayList().toJson());
    }

}
