package ui.display;

import model.Track;

import java.util.Scanner;

/*
The UI logic for project. Provides a number of menus and the ability
for the user to make choices based on them.
Make calls to ui/menu for actual interaction with player object.
 */

public class TerminalUI {

    private boolean isRunning;
    private Scanner scan;
    private Menu menu;
    private boolean loadLastSession;
    int usrInput;

    public TerminalUI() {
        menu = new Menu();
        menu.loadState();
        if (!menu.getPlayer().getCurrentTrack().equals(null)) {
            loadLastSession = true;
        }
        usrInput = 0;
    }

    @SuppressWarnings("methodlength")
    /* EFFECTS: Runs the actual menu system. Provides user with choices
    and takes their input, resulting in a variety of effects.

    MODIFIES: this, player.

     */
    public void run() {
        isRunning = true;
        System.out.println("Welcome" + "\n"
                + "Please enter a choice in the menu");
        System.out.println("1. Display list of playable music files" + "\n"
                + "2. Exit");
        System.out.print("Your choice: ");
        scan = new Scanner(System.in);
        usrInput = scan.nextInt();

        while (isRunning) {
            switch (usrInput) {
                case 1 : // Display list of playable music files
                    menu.displayList();
                    postDisplayListMenu();
                    break;
                case 2 : // Exit
                    isRunning = false;
                    break;
                default :
                    System.out.println("Please enter a valid menu choice");
            }
        }
        scan.close();
    }

    public void clear() {
        for (int i = 0; i < 30; i++) {
            System.out.print("\n");
        }
    }

    @SuppressWarnings("methodlength")
    /* EFFECTS: Runs the menu system after user chooses to display tracks.
    Provides user with choices and takes their input, resulting in a variety of effects.

    MODIFIES: this, player.

    Note: This method is necessarily long, as it contains most of the logic for a menu
     */
    public void postDisplayListMenu() {
        // If we've loaded from file and there was a previously saved state,
        if (loadLastSession) {
            loadLastSession = false;
            // menu.getPlayer().pauseResumeTrack();
            postPlayTrack();
        }

        while (isRunning) {
            clear();
            menu.displayList();
            System.out.println("1. Play track" + "\n"
                    + "2. Add track to queue" + "\n"
                    + "3. Make new playlist" + "\n"
                    + "4. Load playlist" + "\n"
                   // + "5. Add tracks to playlist" + "\n"
                    + "5. Start play from queue" + "\n"
                    + "6. Exit" + "\n");

            System.out.println(menu.displayQueue());

            System.out.print("Please enter your choice: ");
            usrInput = scan.nextInt();

            switch (usrInput) {
                case 1 : // Play track by number
                    System.out.print("Enter track number: ");
                    usrInput = scan.nextInt();
                    menu.startPlay(usrInput);
                    postPlayTrack();
                    break;
                case 2 : // Add track number to queue
                    System.out.print("Enter track number: ");
                    usrInput = scan.nextInt();
                    menu.addTrack(usrInput);
                    break;
                case 3 :
                    System.out.print("Enter playlist name: ");
                    String plName1 = scan.next();
                    //System.out.print("Enter playlist tags as comma separated list, if any: ");
                    //String plTags = scan.nextLine();
                    menu.makePlayList(plName1); //interpretCommaSepList(plTags)
                    scan.nextLine();
                    break;
                case 4:
                    System.out.print("Enter playlist name to load: ");
                    menu.getPlayer().loadFromPlaylist(menu.loadPlayList(scan.next()));
                    break;
                /* case 5:
                    System.out.print("Enter playlist name to add tracks to: ");
                    String plName2 = scan.next();
                    System.out.print("Enter tracks you wish to add as comma separated list: ");
                    String trackList = scan.nextLine();
                    menu.addToPlayList(plName2, interpretCommaSepList(trackList));
                    scan.nextLine();
                    break;
                 */
                case 5 : // Start play from queue
                    menu.startPlay();
                    postPlayTrack();
                    // Queue honestly might be hard to figure out exactly.
                    break;
                case 6 : // Exit
                    isRunning = false;
                    menu.saveState();
                    break;
                default :
                    System.out.println("Please enter a valid menu choice");
            }
        }
        scan.close();
    }

    @SuppressWarnings("methodlength")
        /* EFFECTS: Runs the menu system after play has started. Provides user with choices
    and takes their input, resulting in a variety of effects.

    MODIFIES: this, player.

    Note: This method is necessarily long, as it contains most of the logic for a menu
     */
    public void postPlayTrack() {
        boolean goBack = false;
        while (!goBack) {
            clear();
            menu.displayList();

            System.out.println(menu.displayQueue());

            displayProgress();

            System.out.println("1. Play/Pause track" + "\n"
                    + "2. Stop track" + "\n"
                    + "3. Skip track" + "\n"
                    + "4. Exit" + "\n");
            System.out.print("Please enter your choice: ");
            usrInput = scan.nextInt();

            switch (usrInput) {
                case 1 :
                    menu.playPause();
                    break;
                case 2:
                    menu.stopTrack();
                    goBack = true;
                    break;
                case 3:
                    menu.stopTrack();
                    if (!menu.startPlay()) {
                        goBack = true;
                    }
                    break;
                case 4:
                    goBack = true;
                    isRunning = false;
                    menu.saveState();
                    break;
                default :
                    System.out.println("Please enter a valid menu choice");
            }
        }
    }

    /* REQUIRES: tags be valid string
    EFFECTS: returns a string array by splitting tags around the commas
     */
    public String[] interpretCommaSepList(String list) {
        // any finite concatenation of whitespace followed by the same
        // allows for no whitespace, as per * operator
        return list.split("\\s*,\\s*");
    }

    /* REQUIRES: currentTrack be instantiated
    EFFECTS: Prints out a simple ascii display of how far through the track the player is
     */
    public void displayProgress() {
        Track currentTrack = menu.getPlayer().getCurrentTrack();

        long currentPos = currentTrack.accessClip().getMicrosecondPosition();
        long totalLength = currentTrack.accessClip().getMicrosecondLength();
        // Convoluted way of getting "m:s / m:s"

        String currentTime = getCurrentTime(currentPos, totalLength);

        char[] displayBar = new char[30];
        displayBar[0] = '[';
        displayBar[1] = '#';
        displayBar[29] = ']';
        long percentFinished = (100 * currentPos) / totalLength;

        for (int i = 2; i < (int)(percentFinished * 0.28); i++) {
            displayBar[i] = '#';
        }

        // Turn the char array into String
        String displayString = new String(displayBar);
        System.out.println(currentTrack.getTrackName() + ": " + displayString + "  " + currentTime);
    }

    /* REQUIRES: the current position and total length of a track
    EFFECTS: returns the 4 digit format for time progress through track
     */
    public String getCurrentTime(long currentPos, long totalLength) {

        // Exploit ternary operators so that we always have 4 digit time format
        String currentMin = (currentPos / 1000000) / 60 < 10 ? "0" + (currentPos / 1000000) / 60
                : (currentPos / 1000000) / 60 + "";
        String currentSec = (currentPos / 1000000) % 60 < 10 ? "0" + (currentPos / 1000000) % 60
                : (currentPos / 1000000) % 60 + "";
        String totalMin = (totalLength / 1000000) / 60 < 10 ? "0" + (totalLength / 1000000) / 60
                : (totalLength / 1000000) / 60 + "";
        String totalSec = (totalLength / 1000000) % 60 < 10 ? "0" + (totalLength / 1000000) % 60
                : (totalLength / 1000000) % 60 + "";

        return currentMin + ":" + currentSec
                + " / " + totalMin + ":" + totalSec;
    }

    public Menu getMenu() {
        return menu;
    }
}
