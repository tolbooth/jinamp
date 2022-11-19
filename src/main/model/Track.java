package model;

import org.json.JSONObject;
import persistence.Writeable;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

// Represents a track in the audio player, with associated name and audio data.
public class Track implements Writeable {

    private String trackName;
    private String artistName;
    private File associatedFile;
    private Clip clip;


    // REQUIRES: file be mono WAV, or other supported audio format by Clip
    // EFFECTS: Creates a track using provided file object. Track acts as
    // wrapped around Clip object, which allows playback functionality for
    // associated file.
    public Track(File file) {
        // Do not want to deal with improperly constructed files
        if (file.equals(null)) {
            throw new IllegalArgumentException("File provided to Track constructor null");
        }
        // Get default track name from file, set default artist name.
        trackName = file.getName();
        artistName = "Unknown Artist";
        associatedFile = file;

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
        } catch (LineUnavailableException e) {
            System.out.println("Trouble getting clip from AudioSystem");
            e.printStackTrace();
        } catch (IOException p) {
            System.out.println("IO issue");
            p.printStackTrace();
        } catch (UnsupportedAudioFileException q) {
            System.out.println("Issue instantiating track with provided filename");
            q.printStackTrace();
        }
    }

    public void setTrackName(String name) {
        if (name.equals(null)) {
            throw new IllegalArgumentException("Track name string provided is null");
        }
        trackName = name;
    }

    public void setArtistName(String name) {
        if (name.equals(null)) {
            throw new IllegalArgumentException("Artist name string provided is null");
        }
        artistName = name;
    }

    public Clip accessClip() {
        return clip;
    }

    public File getAssociatedFile() {
        return associatedFile;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("filename", associatedFile.getPath());
        json.put("trackName", trackName);
        json.put("artistName", artistName);
        return json;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Track)) {
            return false;
        } else {
            Track otherTrack = (Track) other;
            if (otherTrack.trackName == this.trackName
                    && otherTrack.artistName == this.artistName) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 19 * hash + artistName.hashCode();
        hash = 19 * hash + trackName.hashCode();
        hash = 19 * hash + associatedFile.hashCode();

        return hash;
    }

    @Override
    public String toString() {
        return artistName + " - " + trackName;
    }
}
