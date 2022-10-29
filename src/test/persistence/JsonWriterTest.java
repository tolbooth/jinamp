package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import model.*;

public class JsonWriterTest {
    Player player;

    @BeforeEach
    void init() {
        player = new Player();
    }


    @Test
    void testWritePlayList() {
        try {
            JsonWriter writer = new JsonWriter("./././data/testPlaylist.json");
            PlayList pl = new PlayList("beef", "corn", "pozole");
            pl.add("nicolasJaarPassTheTime.wav");

            writer.open();
            writer.writePlayList(pl);
            writer.close();

            JsonReader reader = new JsonReader("./././data/testPlaylist.json");
            PlayList pl2 = reader.readPlayList();

            assertEquals(pl2.getTrackList().getFirst().getTrackName(), "nicolasJaarPassTheTime.wav");
            assertNotEquals(pl2.getTags()[0], "untagged");
        } catch (IOException e) {
            fail("testWritePlayList should not throw exception");
        }
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./././data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoCurrentTrack() {
        try {
            JsonWriter writer = new JsonWriter("./././data/testWriterNoCurrentTrack.json");

            writer.open();
            writer.writePlayer(player);
            writer.close();

            JsonReader reader = new JsonReader("./././data/testWriterNoCurrentTrack.json");
            player = reader.readPlayer();
            assertNull(player.getCurrentTrack());
            assertEquals(0, player.getCurrentPosition());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTypicalPlayer() {
        try {
            player.assignTrack(new Track(new File("./././data/nicolasJaarDivorce.wav")));
            JsonWriter writer = new JsonWriter("./././data/testWriterTypicalPlayer.json");
            player.playTrack(player.getCurrentTrack());
            player.pauseResumeTrack();
            long pos = player.getCurrentPosition();

            writer.open();
            writer.writePlayer(player);
            writer.close();

            JsonReader reader = new JsonReader("./././data/testWriterTypicalPlayer.json");
            player = reader.readPlayer();
            assertEquals("nicolasJaarDivorce.wav", player.getCurrentTrack().getTrackName());
            assertEquals(pos, player.getCurrentPosition());

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
