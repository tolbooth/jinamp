package ui.display;

import model.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
Core element of the UI that actually interacts with the Player.
Serves as the link between user interaction and functionality
 */

public class Menu {


    private File directory;
    private String[] listOfFileNames;
    private ArrayList<File> playableFiles;
    private Player player;

    /* EFFECTS: instantiates a menu object with relevant information to
    working directory for accessing files. Also creates a list of
    playable files.
    REQUIRES: The working directory always has at least one file in it.
     */
    public Menu() {
        directory = new File("./././data");
        listOfFileNames = directory.list();
        playableFiles = new ArrayList<>();
        player = new Player();

        // setup makes the run function much simpler
        for (String fileName : listOfFileNames) {
            if (fileName.endsWith(".wav")) {
                // The basic idea here is to create a new file pointing to our playable file.
                // we have the directory file object, so we get its relative path, then
                // append the filename, with the extra "/" required by nature of how paths are
                // stored.
                playableFiles.add(new File(directory.getPath().concat("/" + fileName)));
            }
        }
    }

    /* EFFECTS: Prints a list of all playable files to console
     */
    public void displayList() {
        int i = 1;
        for (File file : playableFiles) {
            System.out.println(i++ + ". " + file.getName());
        }
        for (i = 0; i < 2; i++) {
            System.out.print("\n");
        }
    }

    /* EFFECTS: Displays current queue if it has any tracks
     */
    public String displayQueue() {
        String queue = "";
        if (!player.getQueue().isEmpty()) {
            queue = queue.concat("Current Queue: " + "\n");
            int i = 1;
            for (Track track : player.getQueue()) {
                queue = queue.concat(i++ + ": " + track.getTrackName() + "\n");
            }
        }
        return queue;
    }


    /* REQUIRES: instantiated track, indexing starts from 1
    EFFECTS: Adds track to player's play queue
    MODIFIES: this
     */
    public void addTrack(int num) {
        player.enqueueTrack(new Track(playableFiles.get(num - 1)));
    }

    /* REQUIRES: instantiated track, indexing starts from 1
    EFFECTS: starts playback of track
    MODIFIES: this
     */
    public void startPlay(int num) {
        try {
            player.playTrack(new Track(playableFiles.get(num - 1)));
        } catch (Exception e) {
            e.getCause();
            e.getStackTrace();
        }
    }

    /* REQUIRES: player queue having elements
    EFFECTS: Starts play from the first element of the queue
    Also returns true if the queue is not empty, otherwise returns false.
    MODIFIES: this
     */
    public boolean startPlay() {
        if (!player.getQueue().isEmpty()) {
            try {
                player.playTrack(player.dequeueTrack());
                return true;
            } catch (Exception e) {
                e.getCause();
                e.getStackTrace();
            }
        }
        return false;
    }


    /* EFFECTS: Allows easy access to pause/resume method for player
    MODIFIES: this
     */
    public void playPause() {
        try {
            player.pauseResumeTrack();
        } catch (Exception e) {
            e.getCause();
            e.getStackTrace();
        }
    }

    /* EFFECTS: Allows easy access to stop method for player
    MODIFIES: this
     */
    public void stopTrack() {
        try {
            player.stopTrack();
        } catch (Exception e) {
            e.getCause();
            e.getStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public File getDirectory() {
        return this.directory;
    }
}
