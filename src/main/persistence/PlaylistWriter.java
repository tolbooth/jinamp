package persistence;

import model.Playlist;
import org.json.JSONObject;

// Represents a playlist writer. Inherits most functionality from JsonWriter
public class PlaylistWriter extends JsonWriter {

    public PlaylistWriter(String path) {
        super(path);
    }

    @Override
    public void write(Writeable playlist) {
        JSONObject json = ((Playlist)playlist).toJson();
        saveToFile(json.toString());
    }

}
