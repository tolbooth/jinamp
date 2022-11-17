package model;

import persistence.*;
import ui.display.AddTrackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileHandler implements SeesFiles {
    private PlayerReader playerRead;
    private PlayerWriter playerWrite;
    private PlaylistReader playlistRead;
    private PlaylistWriter playlistWrite;
    private LibraryReader libraryRead;
    private LibraryWriter libraryWrite;
    private TrackBuilder trackBuilder;
    private static final File directory = new File(FILE_PATH);
    private boolean isSetup = false;

    // TODO: Get this implementation working.
    // focus on the dynamics of file reading, between
    // savestates and playlists. How does this interface
    // with library?
    public FileHandler() {
    }

    // EFFECTS: Reads from file all present playlists
    // MODIFIES: list
    public ArrayList<Playlist> retrievePlaylists() throws IOException {
        ArrayList<Playlist> list = new ArrayList<>();
        for (String fileName: directory.list()) {
            if (fileName.endsWith(PLAYLIST_FILE_EXTENSION)) {
                list.add((Playlist)read(fileName));
            }
        }
        return list;
    }

    // EFFECTS: Gets a list of playable files from data directory
    public ArrayList<String> getPlayableFiles() {
        ArrayList<String> playableFiles = new ArrayList<>();
        for (String s : directory.list()) {
            if (s.endsWith(TRACK_FILE_EXTENSION)) {
                playableFiles.add(s);
            }
        }
        return playableFiles;
    }

    // EFFECTS: writes given player to save state json file.
    // MODIFIES: this
    public void write(Player player) {
        try {
            playerWrite = new PlayerWriter(FILE_PATH + SAVE_STATE_NAME);
            playerWrite.open();
            playerWrite.write(player);
            playerWrite.close();
        } catch (FileNotFoundException e) {
            System.out.println("PlayerWriter unable to find save state file.");
        }
    }

    // EFFECTS: writes given playlist to playlist json file with given filename.
    // MODIFIES: this
    public void write(Playlist playlist, String fileName)  {
        try {
            playerWrite = new PlayerWriter(FILE_PATH + fileName + PLAYLIST_FILE_EXTENSION);
            playerWrite.open();
            playerWrite.write(playlist);
            playerWrite.close();
        } catch (FileNotFoundException e) {
            System.out.println("PlayerWriter unable to create file with specified name");
        }
    }

    public void write(Library library) {
        try {
            libraryWrite = new LibraryWriter(FILE_PATH + LIBRARY_STATE_FILE_NAME);
            libraryWrite.open();
            libraryWrite.write(library);
            libraryWrite.close();
        } catch (FileNotFoundException e) {
            System.out.println("LibraryWriter unable to find save state file.");
        }
    }

    // EFFECTS: Helper method for readPlayer, read Playlist. Takes a source filename and
    // determines what the appropriate method is. Throws exception if source file does
    // not conform to naming standards
    public Writeable read(String source) throws IOException {
        if (source.endsWith(PLAYLIST_FILE_EXTENSION)) {
            return readPlayer(FILE_PATH + source);
        } else if (source.endsWith(SAVE_STATE_FILE_EXTENSION)) {
            return readPlaylist(FILE_PATH + source);
        } else if (source.endsWith(LIBRARY_STATE_FILE_EXTENSION)) {
            return readLibrary(FILE_PATH + source);
        } else {
            throw new IOException("Expected valid source string. Must point to save state or playlist");
        }
    }

    private Player readPlayer(String source) throws IOException {
        playerRead = new PlayerReader(source);
        return playerRead.read();
    }

    private Playlist readPlaylist(String source) throws IOException {
        playlistRead = new PlaylistReader(source);
        return playlistRead.read();
    }

    private Library readLibrary(String source) throws IOException {
        libraryRead = new LibraryReader(source);
        return libraryRead.read();
    }
}
