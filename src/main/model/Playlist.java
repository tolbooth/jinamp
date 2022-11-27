package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.io.File;
import java.util.*;

/*
Represents a playlist that is stored as a JSON file
 */

public class Playlist implements Writeable {
    private ArrayList<Track> trackList;
    private String name;
    private Set<String> tags;

    // EFFECTS: Instantiates a playlist with the given name
    public Playlist(String name) {
        if (name.equals(null)) {
            throw new IllegalArgumentException("Playlist name must not be null");
        }

        trackList = new ArrayList<>();
        this.name = name;
    }

    // EFFECTS: Instantiates playlist with given name and tags
    public Playlist(String name, HashSet<String> tags) {
        this(name);
        this.tags = tags;
    }

    // EFFECTS: Adds given tags to playlist. Throw no exception for null tags,
    // Just don't add anything.
    public void addTags(HashSet<String> tags) {
        if (!tags.equals(null)) {
            this.tags.addAll(tags);
        }
    }

    public boolean hasNext(int i) {
        return i < trackList.size() - 1;
    }

    public boolean hasPrev(int i) {
        return i > 0;
    }

    // EFFECTS: Returns an arraylist containing all tracks in playlist
    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public String getName() {
        return name;
    }

    public void addTrack(Track track) {
        trackList.add(track);
        EventLog.getInstance().logEvent(new Event("Track "
                + track.toString() + " added to Playlist."));
    }

    public void addTrack(Track track, int i) {
        try {
            trackList.add(i, track);
            EventLog.getInstance().logEvent(new Event("Track "
                    + track.toString() + " added to Playlist at position " + i + "."));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void clearTracklist() {
        trackList.clear();
        EventLog.getInstance().logEvent(new Event("Playlist refreshed."));
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
