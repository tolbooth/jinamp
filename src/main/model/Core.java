package model;

import ui.display.*;
import persistence.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class Core implements SeesFiles {
    private Library library;
    private Player player;
    private FileHandler fileHandler;
    private ArrayList<String> unassignedFiles;


    // EFFECTS: Instantiates an object representing the core of the player.
    // Initializes resources needed for UI to interact with various components
    //
    public Core() {
        try {
            library = new Library();
            fileHandler = new FileHandler();
            library = (Library)fileHandler.read(LIBRARY_STATE_FILE_NAME);
            library.assignPlaylists(fileHandler.retrievePlaylists());
            player = (Player)fileHandler.read(SAVE_STATE_NAME);
            // unassignedFiles initially stores every playable file in the
            // data directory.
            this.unassignedFiles = fileHandler.getPlayableFiles();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // We only want to pass our addTrackPane a list of filenames
    // that have NOT already been associated with a track.
    // What's the most efficient way to do this? NOT this way.

    // EFFECTS: Checks whether there are any playable files in the data folder
    // that have not been associated with a Track. ArrayList passed in contains
    // those file names as String
    // MODIFIES: unassignedFiles
    public boolean checkNewTracks() {
        for (Track t : library.getTrackList()) {
            unassignedFiles.remove(t.getAssociatedFile().getPath());
        }
        return unassignedFiles.size() > 0;
    }

    public Library getLibrary() {
        return library;
    }

    public Player getPlayer() {
        return player;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public ArrayList<String> getUnassignedFiles() {
        return unassignedFiles;
    }
}
