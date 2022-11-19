package model;

import org.json.JSONObject;
import persistence.Writeable;

/*
Represents the core audio player of the project. Handles track objects and provides
functionality for their manipulation
 */

public class Player implements Writeable {

    private Playlist currentPlaylist;
    private int playListPosition;
    private Track currentTrack;
    private long currentPosition;

    // EFFECTS: Instantiates a player with given information
    // Note: Player must only be read in from file.
    public Player(Track cr, long cp, int playlistPosition, Playlist playlist) {
        currentTrack = cr;
        currentPlaylist = playlist;
        currentPosition = cp;
        playListPosition = playlistPosition;
        currentTrack.accessClip().setMicrosecondPosition(currentPosition);
    }

//      EFFECTS: Starts playback of track
//      MODIFIES: this
    public void playTrack() {
        if (!currentTrack.accessClip().isActive()) {
            // Set the playback position in track to currentPosition
            currentTrack.accessClip().setMicrosecondPosition(currentPosition);
            currentTrack.accessClip().start();
        }
    }

    //      EFFECTS: Starts playback of track
    //      MODIFIES: this
    public void playTrack(int i) {
        // Set the playback position in track to currentPosition
//        if (currentTrack.accessClip().isActive()) {
//            currentTrack.accessClip().stop();
//        }
        assignTrack(currentPlaylist.getTrack(i));
        currentTrack.accessClip().setMicrosecondPosition(currentPosition);
        currentTrack.accessClip().start();
    }

//      EFFECTS: Pauses playback of track
//      MODIFIES: this
    public boolean pauseTrack() {
        if (currentTrack.accessClip().isActive()) {
            // Store current position before stopping playback
            currentPosition = currentTrack.accessClip().getMicrosecondPosition();
            currentTrack.accessClip().stop();
        }
        return false;
    }

//      EFFECTS: Stops playback of track.
//      MODIFIES: this
    public void stopTrack() {
        currentTrack.accessClip().stop();
        currentTrack.accessClip().setFramePosition(0);
        currentPosition = 0;
    }

//      EFFECTS: Moves play to next track in playlist
//      MODIFIES: this
    public void nextTrack() {
        stopTrack();
        if (currentPlaylist.hasNext(playListPosition)) {
            assignTrack(currentPlaylist.getTrack(++playListPosition));
            playTrack();
        }
    }

//      EFFECTS: Moves play to previous track in playlist
//      MODIFIES: this
    public void previousTrack() {
        stopTrack();
        if (currentPlaylist.hasPrev(playListPosition)) {
            assignTrack(currentPlaylist.getTrack(--playListPosition));
            playTrack();
        }
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

    // EFFECTS: updates current position with clip position
    public void updateCurrentPosition() {
        currentPosition = currentTrack.accessClip().getMicrosecondPosition();
    }

    // EFFECTS: returns the 4 digit format for time progress through track
    public String timeToString() {
        long totalLength = currentTrack.accessClip().getMicrosecondLength();
        // Exploit ternary operators so that we always have 4 digit time format
        String currentMin = (currentPosition / 1000000) / 60 < 10 ? "0" + (currentPosition / 1000000) / 60
                : (currentPosition / 1000000) / 60 + "";
        String currentSec = (currentPosition / 1000000) % 60 < 10 ? "0" + (currentPosition / 1000000) % 60
                : (currentPosition / 1000000) % 60 + "";
        String totalMin = (totalLength / 1000000) / 60 < 10 ? "0" + (totalLength / 1000000) / 60
                : (totalLength / 1000000) / 60 + "";
        String totalSec = (totalLength / 1000000) % 60 < 10 ? "0" + (totalLength / 1000000) % 60
                : (totalLength / 1000000) % 60 + "";

        return currentMin + ":" + currentSec
                + " / " + totalMin + ":" + totalSec;

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
