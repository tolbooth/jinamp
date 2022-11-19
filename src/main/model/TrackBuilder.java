package model;

import java.io.File;


// Represents a constructor helper for Track objects.
// Streamlines Track creation and management.
public class TrackBuilder implements SeesFiles {

    public TrackBuilder(){
    }

    // EFFECTS: Creates a track for given file path, and assigns it given track
    // name and artist name. Returns said track
    // REQUIRES: filepath be a valid file, trackName and artistName be non null
    public Track buildTrack(String filePath, String trackName, String artistName) {
        Track track = new Track(new File(filePath));
        track.setTrackName(trackName);
        track.setArtistName(artistName);
        return track;
    }
}
