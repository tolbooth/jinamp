package persistence;


import java.io.*;
import model.*;
import org.json.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 Modeled on JsonSerializationDemo from the CPSC210 github repo.
 Represents a reader that reads json from a written file and
 instantiates a new player with that.
 */

public class JsonReader {
    private File file;

    // REQUIRES: fileName be full abstract path to target file
    public JsonReader(String fileName) {
        this.file = new File(fileName);
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(file.getPath());
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        // Check that there is a current track to load
        String trackName = jsonObject.getString("track");
        if (!trackName.equals("null")) {
            File file = new File("./././data/"
                    + trackName);
            Track track = new Track(file);
            long pos = jsonObject.getLong("position");
            return new Player(track, pos);
        }
        // implicit else -- just make new player
        return new Player();
    }
}
