package ui;

import model.Core;
import ui.display.AddTrackPane;
import ui.display.UI;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
    The main method. Starts the UI.
 */
public class Main {
    public static void main(String[] args) {
        Core core = new Core();

        if (core.checkNewTracks()) {
            TestThread testThread = new TestThread(core);
            try {
                testThread.join();
                core.getFileHandler().write(core.getLibrary());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //UI userInterface = new UI(core.getLibrary(), core.getPlayer(), core.getUserFunctions());
    }
}


