package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a library object. Contains data and methods relating to
// the management of tracks and playlists.
public class Library implements Writeable {
    private ArrayList<Track> trackList;
    private ArrayList<Playlist> playlists;
    private boolean allTracksSetup = false;


    // EFFECTS: Instantiates new library with a fileHandler. Obtains tracklist and playlists
    // from said handler.
    public Library() {
        this.trackList =  new ArrayList<>();
        playlists = new ArrayList<>();
        playlists.add(new Playlist("default"));
    }

    // EFFECTS: Returns track with specified track name. If track not in library, throws exception.
    public Track getTrack(String trackName) throws TrackNotInLibraryException {
        for (Track t : trackList) {
            if (t.getTrackName().equals(trackName)) {
                return t;
            }
        }
        throw new TrackNotInLibraryException();
    }

    public void assignTrackList(ArrayList<Track> trackList) {
        this.trackList = trackList;
    }

    public void assignPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<Track> getTrackList() {
        return this.trackList;
    }

    public ArrayList<Playlist> getPlaylists() {
        return this.playlists;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray trackListJArr = new JSONArray();
        for (Track t: trackList) {
            trackListJArr.put(t.toJson());
        }
        json.put("library", trackListJArr);
        return json;
    }

    public class TrackNotInLibraryException extends IllegalArgumentException {

    }
}
