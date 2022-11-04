package model;


// NOTES:
//  (1) How should Queue/Playlist work? Is every Playlist just a
//      persistent wrapper around a queue? Or should we take the
//      winamp approach and have everything be a playlist?
//      -   If we treat everything as a playlist, then playlists need to fulfill a few
//          requirements. Consider an implementation of playlists with a track stream,
//          and player reads that stream. For display purposes obviously we need to
//          have access to all tracks at once, so does that make sense?
//      -   Playlist must be able to be written and read from file, with a name, tags,
//          and contents (a stream or arraylist of tracks).
//  (2)
//
//
//
//
//
//
//
//
//
//
//
//


public class Core {
}
