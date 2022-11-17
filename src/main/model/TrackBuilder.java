package model;

import java.io.File;


// Represents a constructor helper for Track objects.
// Streamlines Track creation and management.
public class TrackBuilder implements SeesFiles {

    public TrackBuilder(){

    }

    public Track buildTrack(String filePath, String trackName, String artistName) {
        Track track = new Track(new File(filePath));
        track.setTrackName(trackName);
        track.setArtistName(artistName);
        return track;
    }

//    public Track buildTrack(String trackName) {
//        Track track = new Track(new File(FILE_PATH + trackName + TRACK_FILE_EXTENSION));
//        track.setTrackName(spaceCamelCaseString(trackName));
//        return track;
//    }

//    // EFFECTS: Takes camelcase string and adds spaces. Has no effect on already spaced strings.
//    private String spaceCamelCaseString(String string) {
//        if (string.equals(null)) {
//            throw new IllegalArgumentException("Must provide instantiated track name string");
//        }
//        StringBuilder strBuild = new StringBuilder();
//        // regex source: https://stackoverflow.com/a/7594052
//        for (String str : string.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
//            strBuild.append(str);
//            strBuild.append(" ");
//        }
//        // Easiest way to accomplish having no space at the end is just to delete the last one
//        // rather than having complicated loop logic
//        strBuild.deleteCharAt(strBuild.length() - 1);
//        return strBuild.toString();
//    }

}
