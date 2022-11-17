package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
Represents a playlist that is stored as a JSON file
 */

public class Playlist implements Writeable {
    private List<Track> trackList;
    private String name;
    private Set<String> tags;

    public Playlist(String name) {
        if (name.equals(null)) {
            throw new IllegalArgumentException("Playlist name must not be null");
        }

        trackList = new LinkedList<>();
        this.name = name;
    }

    //      EFFECTS: Instantiates playlist with given name and tags
    public Playlist(String name, HashSet<String> tags) {
        this(name);
        this.tags = tags;
    }

//      EFFECTS: Adds given tags to playlist. Throw no exception for null tags,
//      Just don't add anything.
    public void addTags(HashSet<String> tags) {
        if (!tags.equals(null)) {
            this.tags.addAll(tags);
        }
    }

    public String getName() {
        return name;
    }

    public void addTrack(Track track) {
        trackList.add(track);
    }

    public Boolean removeTrack(Track track) {
        return trackList.remove(track);
    }

    public Track getTrack(int i) {
        return trackList.get(i);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray trackJArr = new JSONArray();
        // is this the way to do it?
        for (Track t : trackList) {
            trackJArr.put(t.toJson());
        }
        JSONArray tagJArr = new JSONArray(tags);
        json.put("name", name);
        json.put("tags", tagJArr);
        json.put("tracks", trackJArr);
        return json;
    }
}