package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a library object. Contains data and methods relating to
// the management of tracks and playlists.


//TODO: Add some sort of lookup to library. Store trackList as json, provide way of other readers/writers access to hash
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

    // Not sure if this is necessary, if the trackList is being passed around by reference presumable we
    // can just always modify it and rely on the values being modified everywhere
//    public void addTrack(Track t) {
//        // Note: This should work fine for FileHandler as trackList is passed by reference
//        this.trackList.add(t);
//    }

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
        json.put("library:", trackListJArr);
        return json;
    }

    public class TrackNotInLibraryException extends IllegalArgumentException {

    }
}
