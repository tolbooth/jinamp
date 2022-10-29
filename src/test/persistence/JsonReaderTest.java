package persistence;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import model.*;

public class JsonReaderTest {

    @Test
    void testReaderPlayList() {
        JsonReader plReader = new JsonReader("./././data/testPlaylist.json");
        try {
            PlayList pl1 = plReader.readPlayList();
            assertNotEquals(pl1.getTags(), new String[]{"untagged"});
        } catch (IOException e) {
            fail("couldn't construct from playlist file");
        }
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./././data/noSuchFile.json");
        try {
            Player player = reader.readPlayer();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./././data/testReaderNoCurrentTrack.json");
        try {
            Player player = reader.readPlayer();
            assertNull(player.getCurrentTrack());
            assertEquals(0, player.getCurrentPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./././data/testReaderTypicalPlayer.json");
        try {
            Player player = reader.readPlayer();
            assertEquals("nicolasJaarThreeWindows.wav", player.getCurrentTrack().getTrackName());
            assertEquals(7000000, player.getCurrentPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

