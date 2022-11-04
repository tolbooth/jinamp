package ui;

import model.Player;
import model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.deprecated.Menu;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    Player player;
    Menu menu;

    @BeforeEach
    void init() {
        menu = new Menu();
    }


    @Test
    void saveLoadStateTest() {
        Menu testMenu = new Menu();
        testMenu.getPlayer().assignTrack(new Track(new File("./././data/nicolasJaarPassTheTime.wav")));
        testMenu.saveState();

        testMenu.loadState();
        assertEquals("nicolasJaarPassTheTime.wav", testMenu.getPlayer().getCurrentTrack().getTrackName());
        assertEquals(0, testMenu.getPlayer().getCurrentPosition());
    }
}
