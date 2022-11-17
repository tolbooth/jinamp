package persistence;

import model.FileHandler;
import model.Library;
import model.Track;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LibraryReader extends JsonReader {

    public LibraryReader(String source) {
        super(source);
    }

    @Override
    public Library read() throws IOException {
        String jsonData = readFile(file.getPath());
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: parses library from jsonobject and instantiates with tracklist
    private Library parseLibrary(JSONObject jsonObject) {
        JSONArray trackJArr = jsonObject.getJSONArray("library");
        ArrayList<Track> trackList = new ArrayList<>();
        for (int i = 0; i < trackJArr.length(); i++) {
            trackList.add(parseTrack(trackJArr.getJSONObject(i)));
        }
        Library lib = new Library();
        lib.assignTrackList(trackList);
        return lib;
    }
}
