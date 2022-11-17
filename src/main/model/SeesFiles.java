package model;

import java.io.File;
import java.util.List;

// Represents an object that interfaces with file objects or reaches
// into the filesystem
public interface SeesFiles {
    static final String FILE_PATH = "./././data/";
    static final String TRACK_FILE_EXTENSION = ".wav";
    static final String TRACK_STATE_FILE_EXTENSION = ".tk.json";
    static final String PLAYLIST_FILE_EXTENSION = ".pl.json";
    static final String SAVE_STATE_FILE_EXTENSION = ".ss.json";
    static final String SAVE_STATE_NAME = "Player" + SAVE_STATE_FILE_EXTENSION;
    static final String LIBRARY_STATE_FILE_EXTENSION = ".lb.json";
    static final String LIBRARY_STATE_FILE_NAME = "Library" + LIBRARY_STATE_FILE_EXTENSION;
}
