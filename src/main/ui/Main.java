package ui;

import ui.display.UI;

import javax.swing.*;

import java.util.Scanner;

/*
    The main method. Starts the UI.
 */
public class Main {
    public static void main(String[] args) {
        JFrame userInterface = new UI();
        userInterface.setVisible(true);
        userInterface.setDefaultCloseOperation(userInterface.EXIT_ON_CLOSE);
    }
}
