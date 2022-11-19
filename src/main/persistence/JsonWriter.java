package persistence;

import model.*;
import java.io.*;
import org.json.*;

/*
 Modeled on JsonSerializationDemo from the CPSC210 github repo.
 Represents a writer that stores current state information in
 a json file
 */
public abstract class JsonWriter {
    protected PrintWriter printWriter;
    protected String path;

    // REQUIRES: fileName be full abstract path to target file
    public JsonWriter(String path) {
        this.path = path;
    }

    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    // NOTE: should probably split this class into playlist and player. append specific
    // file extension to all playlists to make safe
    // MODIFIES: this
    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(new File(path));
    }

    // EFFECTS: writes JSON representation of player to file
    // MODIFIES: this
    public abstract void write(Writeable object);

    // EFFECTS: closes writer
    // MODIFIES: this
    public void close() {
        printWriter.close();
    }

    // EFFECTS: writes string to file
    // MODIFIES: this
    protected void saveToFile(String json) {
        printWriter.print(json);
    }

}
