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

    private LinkedList<Track> playQueue;
    private Track currentTrack;
    private long currentPosition;
    private Boolean isPlaying;


    /* EFFECTS: given a track and a position, resumes
    play. Useful for reopening a session.
     */
    public Player(Track cr, long cp) {
        currentTrack = cr;
        currentPosition = cp;
        currentTrack.accessClip().setMicrosecondPosition(currentPosition);
        isPlaying = false;
        playQueue = new LinkedList<>();
    }

    /* EFFECTS: Sets current track null, and isPlaying to false.
      Creates an empty queue.
    */
    public Player() {
        currentTrack = null;
        currentPosition = 0;
        isPlaying = false;
        playQueue = new LinkedList<>();
    }

    /* REQUIRES: Instantiated track
       EFFECTS: Starts playback of track
       MODIFIES: this
     */
    public void playTrack(Track track) {
        if (!isPlaying) {
            assignTrack(track);
            // Ensure that track plays from position 0.
            currentTrack.accessClip().setFramePosition(0);
            currentTrack.accessClip().start();
            isPlaying = true;
        }
    }

    /* REQUIRES: Instantiated track
       EFFECTS: Pauses or Starts playback of track based on current play state of track
       MODIFIES: this, track
    */
    public boolean pauseResumeTrack() {
        if (isPlaying) {
            // Store current position before stopping playback
            currentPosition  = currentTrack.accessClip().getMicrosecondPosition();
            currentTrack.accessClip().stop();
            isPlaying = false;
        } else {
            // Set the playback position in track to currentPosition
            currentTrack.accessClip().setMicrosecondPosition(currentPosition);
            currentTrack.accessClip().start();
            isPlaying = true;
        }
        return isPlaying;
    }

    /* REQUIRES: Instantiated track
       EFFECTS: Stops playback of track -- sets current track to null.
       New track will need to be found.
       MODIFIES: this, track
    */
    public void stopTrack() {

        currentTrack.accessClip().stop();
        currentTrack.accessClip().setFramePosition(0);
        currentTrack.accessClip().close();

        currentPosition = 0;

        isPlaying = false;
    }

    /* REQUIRES: Instantiated playlist
       EFFECTS: Enqueues tracks from playlist
       MODIFIES: this
     */
    public void loadFromPlaylist(PlayList pl) {
        for (Track t: pl.getTrackList()) {
            enqueueTrack(t);
        }
    }

    /* REQUIRES: Instantiated track
       EFFECTS: Enqueues provided track at end of queue
       MODIFIES: this
     */
    public void enqueueTrack(Track track) {
        playQueue.add(track);
    }

    /* EFFECTS: Dequeues the first track in queue
       MODIFIES: this
     */
    public Track dequeueTrack() {
        return playQueue.removeFirst();
    }

    // REQUIRES: Instantiated track
    public void assignTrack(Track track) {
        currentTrack = track;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (currentTrack != null) {
            json.put("track", currentTrack.getTrackName());
        } else {
            json.put("track", "null");
        }
        json.put("position", currentPosition);
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

    public LinkedList<Track> getQueue() {
        return playQueue;
    }

    public Track getTrack(int i) {
        return playQueue.get(i);
    }


}
