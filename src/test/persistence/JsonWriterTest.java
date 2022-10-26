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
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./././data/testWriterNoCurrentTrack.json");
            player = reader.read();
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
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./././data/testWriterTypicalPlayer.json");
            player = reader.read();
            assertEquals("nicolasJaarDivorce.wav", player.getCurrentTrack().getTrackName());
            assertEquals(pos, player.getCurrentPosition());

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception should not have been thrown");
        }
    }
}
