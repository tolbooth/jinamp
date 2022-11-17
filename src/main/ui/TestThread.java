package ui;

import model.Core;
import ui.display.AddTrackPane;

public class TestThread extends Thread {
    Core core;

    public TestThread(Core core) {
        this.core = core;
    }

    @Override
    public void run() {
        AddTrackPane addTrackPane = new AddTrackPane(core.getUnassignedFiles(),
                core.getLibrary().getTrackList());
        addTrackPane.startGUI();
    }
}
