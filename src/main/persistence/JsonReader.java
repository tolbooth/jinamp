package persistence;


import java.io.*;
import model.*;
import org.json.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

/*
 Modeled on JsonSerializationDemo from the CPSC210 github repo.
 Represents a reader that reads json from a written file and
 instantiates a new player with that.
 */

public abstract class JsonReader {
    protected File file;
    protected TrackBuilder trackBuilder;

    // REQUIRES: fileName be full abstract path to target file
    public JsonReader(String fileName) {
        this.file = new File(fileName);
    }

    public abstract Writeable read() throws IOException;

    // EFFECTS: reads source file as string and returns it
    protected String readFile(String source) throws IOException {
        // String builder useful as it allows mutability
        StringBuilder contentBuilder = new StringBuilder();

        // try-with-resources, populates String stream with all lines from source file
        // assuming UTF_8 characters.
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            // Lambda expression. For each element in stream, append to stringBuilder
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses track from json object
    // MODIFIES: this
    protected Track parseTrack(JSONObject jsonObj) {
        trackBuilder = new TrackBuilder();
        return trackBuilder.buildTrack(jsonObj.getString("filename"),
                jsonObj.getString("trackName"), jsonObj.getString("artistName"));

    }

    //EFFECTS: parses playlist from JSON object and returns it.
    // if add encounters error, tells user no tracks to add
    protected Playlist parsePlayList(JSONObject jsonObject) {
        JSONArray trackJArr = jsonObject.getJSONArray("tracks");
        JSONArray tagJArr = jsonObject.getJSONArray("tags");
        HashSet<String> tags = new HashSet<>();
        ArrayList<Track> tracks = new ArrayList<>();

        for (Object o : tagJArr) {
            tags.add((String)o);
        }
        // cast toArray from object array to string array
        Playlist playlist = new Playlist(jsonObject.get("name").toString(), tags);

        for (int i = 0; i < trackJArr.length(); i++) {
            playlist.addTrack(parseTrack(trackJArr.getJSONObject(i)));
        }
        return playlist;
    }
}
