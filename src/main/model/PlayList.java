package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.io.File;
import java.util.LinkedList;

/*
Represents a playlist that is stored as a JSON file
 */

public class PlayList implements Writeable {
    private LinkedList<Track> trackList;
    private String[] tags;

    // EFFECTS: initializes playlist with new tracklist
    // and associated tags
    public PlayList(String... tags) {
        trackList = new LinkedList<>();
        this.tags = tags;
    }

    // EFFECTS: initializes playlist with new tracklist
    // and marks as untagged
    public PlayList() {
        trackList = new LinkedList<>();
        this.tags = new String[]{"untagged"};
    }

    /* EFFECTS: adds track with name corresponding to str
    to trackList
    MODIFIES: this
    NOTE: probably an issue with how this method adds files. look into this!
    */
    public void add(String str) throws IllegalArgumentException {
        trackList.add(new Track(new File("./././data/" + str)));
    }

    public LinkedList<Track> getTrackList() {
        return this.trackList;
    }

    public String[] getTags() {
        return this.tags;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        int i = 0;
        int j = 0;
        for (Track t : trackList) {
            json.put("id" + i++, t.getTrackName());
        }
        for (String s: tags) {
            json.put("tag" + j++, s);
        }
        return json;
    }
}
