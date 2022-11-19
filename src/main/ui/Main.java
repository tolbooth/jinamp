package ui;

import model.Core;
import model.Track;
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

        // Start UI in the event dispatching thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                core.checkNewTracks();
                UI ui = new UI(core);
                ui.startGUI();
            }
        });
    }
}


