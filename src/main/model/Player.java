package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.util.LinkedList;

//TODO: Implement seek feature
//TODO: Transition from Clip to JLayer
//TODO: Implement menu functionality for manipulating playlists

/*
Represents the core audio player of the project. Handles track objects and provides
functionality for their manipulation
 */

public class Player implements Writeable {

    private Playlist currentPlaylist;
    private int playListPosition;
    private Track currentTrack;
    private long currentPosition;
    private Boolean isPlaying;

    // EFFECTS: Instantiates a player with given information
    // Note: Player must only be read in from file.
    public Player(Track cr, long cp, int playlistPosition, Playlist playlist) {
        currentTrack = cr;
        currentPlaylist = playlist;
        currentPosition = cp;
        currentTrack.accessClip().setMicrosecondPosition(currentPosition);
        isPlaying = false;

    }

//      EFFECTS: Starts playback of track
//      MODIFIES: this
    public void playTrack() {
        if (!isPlaying) {
            // Set the playback position in track to currentPosition
            currentTrack.accessClip().setMicrosecondPosition(currentPosition);
            currentTrack.accessClip().start();
            isPlaying = true;
        }
    }

//      EFFECTS: Pauses playback of track
//      MODIFIES: this
    public boolean pauseTrack() {
        if (isPlaying) {
            // Store current position before stopping playback
            currentPosition = currentTrack.accessClip().getMicrosecondPosition();
            currentTrack.accessClip().stop();
            isPlaying = false;
        }
        return false;
    }

//      EFFECTS: Stops playback of track.
//      MODIFIES: this
    public void stopTrack() {
        currentTrack.accessClip().stop();
        currentTrack.accessClip().setFramePosition(0);
        currentTrack.accessClip().close();
        currentPosition = 0;
        isPlaying = false;
    }

//      EFFECTS: Moves play to next track in playlist
//      MODIFIES: this
    public void nextTrack() {
        stopTrack();
        currentTrack = currentPlaylist.getTrack(playListPosition++);
        playTrack();
    }

//      EFFECTS: Moves play to previous track in playlist
//      MODIFIES: this
    public void previousTrack() {
        stopTrack();
        currentTrack = currentPlaylist.getTrack(playListPosition--);
        playTrack();
    }

//      REQUIRES: Instantiated track
//      EFFECTS: Enqueues provided track at end of queue
//      MODIFIES: this
    public void enqueueTrack(Track track) {
        currentPlaylist.addTrack(track);
    }


//      MODIFIES: this
    public void assignTrack(Track track) {
        if (track.equals(null)) {
            throw new IllegalArgumentException("Must provide instantiated track to player");
        }
        currentTrack = track;
        currentTrack.accessClip().setFramePosition(0);
        currentPosition = 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("track", currentTrack.toJson());
        json.put("position", currentPosition);
        json.put("playlistPosition", playListPosition);
        // Is this what I want to do?
        json.put("playlist", currentPlaylist.toJson());
        return json;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public Track getTrack(int i) {
        return currentPlaylist.getTrack(i);
    }


}
