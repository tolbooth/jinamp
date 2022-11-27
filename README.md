# Simple Audio Player WIP

## General Idea

This project is a simple audio player for listening to your
favorite tunes! Now fleshed out with a Swing GUI and a persistent library!
In the case of this project, I choose to assume the user will only provide uncompressed
MONO .wav files, and not .mp3 simply wrapped as .wav. 

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by adding a new Track to the library
through the AddTrackPane. Input the artist name and track name into required fields, and the associated
file will be added to the library as a Track with those values.
- You can generate the second required event related to adding Xs to a Y by double clicking a Track in
the library pane to add it to the playlist pane.
- You can locate my visual component by... Viewing the player. Buttons have custom graphics.
- You can save the state of my application by... The state of the application is automatically saved
- You can reload the state of my application by... The state of the application is automatically reloaded

## For Who?

This project is for those looking for a lightweight but
limited audio player, with all the basic functionality one
would expect.

## What's the Point?

An audio player is a perfect project for exploring how pieces
of a software system work together, and has is very intuitively
expressed in an object-oriented paradigm.


## User Stories

* As a user, I want to be able to browse my audio files
* As a user, I want to be able to add a track to my queue
* As a user, I want to be able to view the next track in my queue
* As a user, I want to be able to play and pause a track
* As a user, I want to be able to create and save a playlist
* As a user, I want to be able to load a playlist from file

# Phase 4: Task 2

```Sat Nov 26 16:29:33 PST 2022
Sat Nov 26 16:33:13 PST 2022
Track Nicolas Jaar - Three Windows added to Playlist.
Sat Nov 26 16:33:13 PST 2022
Track Nicolas Jaar - Pass The Time added to Playlist.
Sat Nov 26 16:33:18 PST 2022
Track Nicolas Jaar - Three Windows played.
Sat Nov 26 16:33:22 PST 2022
Track Nicolas Jaar - Three Windows stopped.
Sat Nov 26 16:33:22 PST 2022
Track Nicolas Jaar - Pass The Time played.
Sat Nov 26 16:33:37 PST 2022
Track Nicolas Jaar - Pass The Time stopped.
Sat Nov 26 16:33:37 PST 2022
Track Nicolas Jaar - Three Windows played.
Sat Nov 26 16:33:38 PST 2022
Track Nicolas Jaar - Three Windows stopped.
Sat Nov 26 16:33:38 PST 2022
Track Nicolas Jaar - Pass The Time played.
Sat Nov 26 16:33:41 PST 2022
Track Nicolas Jaar - Pass The Time stopped.
```

# Phase 4: Task 3

Thankfully during Phase 3 of the project I took the time to consider ways
in which my code could be structured better, and rewrote the entire
project from close-to-scratch to ensure less messy dependecies. There are
still ways in which the structuring could be improved, however.
One of the main ways I would make the codebase more readable and insodoing
more maintainable, would be to separate the User Interface classes more
effectively. As it stands, though the methods themselves are well compartmentalized,
The overall UI consists of only 2 classes. My original plan was to subdivide
this into classes that represented clusters of functionality in the UI,
i.e. having a class for the Playlist pane, a class for the Library pane,
a class for the Playback controls, etc. 

The other main issue I take with my project is how the AddTrackPane interacts
with the main Player. With a better understanding of threading, I could ensure
that Player never opens if there are tracks to be added, or at least that
the library and playlist update properly in the case of additional tracks being
added. Though this is less to do with refactoring, and more to do with error on 
my part.
