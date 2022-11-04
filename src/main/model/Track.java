package model;

import java.io.File;
import javax.sound.sampled.*;

/* Represents a track in the audio player, with associated name and audio data.

 */

public class Track {

    private String trackName;
    private String artistName;
    private Clip clip;


    /* REQUIRES: file be mono WAV, or other supported audio format by Clip
       EFFECTS: Creates a track using provided file object. Track acts as
       wrapped around Clip object, which allows playback functionality for
       associated file.
     */
    public Track(File file) {
        trackName = file.getName();
        // This method can throw an exception, so we're creating a nice
        // try catch block to hopefully communicate when things go wrong
        try {
            // Read our audio input stream from the file
            AudioInputStream aiS = AudioSystem.getAudioInputStream(file);
            // Instantiate clip object by obtaining Clip from
            // MixerProvider. See AudioSystem javadoc for
            // more information.
            clip = AudioSystem.getClip();
            // Associate clip with audio stream from file
            clip.open(aiS);
        } catch (Exception e) {
            // Get cause and track trace for issue
            e.getCause();
            e.getStackTrace();
        }
    }

    public void setTrackName(String name) {
        trackName = name;
    }

    public void setArtistName(String name) {
        artistName = name;
    }

    public Clip accessClip() {
        return clip;
    }

    public String getTrackName() {
        return trackName;
    }
}
