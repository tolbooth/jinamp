package ui;

import model.Core;
import model.Track;
import ui.display.AddTrackPane;
import ui.display.UI;

import javax.swing.*;
import javax.swing.UIManager;

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

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (ClassNotFoundException e) {
            System.err.println("L&F library may be missing from the class path.");
        } catch (Exception e) {
            System.err.println("Couldn't get Motif look and fee for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        }

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


