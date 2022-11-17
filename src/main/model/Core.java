package model;


// NOTES:
//  (1) How should Queue/Playlist work? Is every Playlist just a
//      persistent wrapper around a queue? Or should we take the
//      winamp approach and have everything be a playlist?
//      -   If we treat everything as a playlist, then playlists need to fulfill a few
//          requirements. Consider an implementation of playlists with a track stream,
//          and player reads that stream. For display purposes obviously we need to
//          have access to all tracks at once, so does that make sense?
//      -   Playlist must be able to be written and read from file, with a name, tags,
//          and contents (a stream or arraylist of tracks).
//  (2) FUNDAMENTAL PROBLEM: HOW DOES LIBRARY INTERACT WITH FILE HANDLER?
//      -   What we want: The user to have to associate all files with an artist name and a track
//          name, and for those Tracks to then be retrievable from the filesystem on startup, and
//          for lookup in playlists, etc.
//      -   How we want to do that: Some GUI popup that shows all files in the library not already
//          associated with a Track in the library. This GUI element closes when the list containing
//          unassociated files is empty, and returns back a list of tracks to the caller method,
//          presumably Core. This list of tracks is then added to the library.
//      -   For this to work: Library must be initialized first. It must be read in from file,
//          meaning we generate a library with an already initialized tracklist. This may or
//          may not contain elements.
//          -   Then, we call the UI element, and add those discrepancies to the library
//          -   Don't muck about with the relationship between FileHandler and library.
//              this is functionality that should be addressed in Core, which has access to all of these
//
//
//
//
//
//


import ui.display.*;
import ui.interaction.*;
import persistence.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class Core implements SeesFiles {
    private Library library;
    private Player player;
    private FileHandler fileHandler;
    private UserFunctions userFunctions;
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
            userFunctions = new UserFunctions();
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

    public UserFunctions getUserFunctions() {
        return userFunctions;
    }

    public ArrayList<String> getUnassignedFiles() {
        return unassignedFiles;
    }
}
