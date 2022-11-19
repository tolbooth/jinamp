package persistence;

import model.Playlist;
import model.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

// Represents JSON reader for Playlist objects
public class PlaylistReader extends JsonReader {

    public PlaylistReader(String fileName) {
        super(fileName);
    }

    @Override
    // EFFECTS: reads playlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Playlist read() throws IOException {
        String jsonData = readFile(file.getPath());
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayList(jsonObject);
    }
}
