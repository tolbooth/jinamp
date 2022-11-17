package persistence;

import model.Player;
import model.Playlist;
import model.Track;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Update implementation to reflect how library interacts w player
public class PlayerReader extends JsonReader {

    public PlayerReader(String fileName) {
        super(fileName);
    }

    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    @Override
    public Player read() throws IOException {
        String jsonData = readFile(file.getPath());
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        // Check that there is a current track to load
        JSONObject trackObj = jsonObject.getJSONObject("track");
        JSONObject jsonPlaylist = jsonObject.getJSONObject("playlist");

        return new Player(parseTrack(trackObj), jsonObject.getLong("position"),
                jsonObject.getInt("playlistPosition"), parsePlayList(jsonPlaylist));
    }

}
