package model;

import java.util.ArrayList;
import java.util.LinkedList;

/*
Represents the core audio player of the project. Handles track objects and provides
functionality for their manipulation
 */

public class Player {

    private LinkedList<Track> playQueue;
    private Track currentTrack;
    private long currentPosition;
    private Boolean isPlaying;

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

        isPlaying = false;
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
