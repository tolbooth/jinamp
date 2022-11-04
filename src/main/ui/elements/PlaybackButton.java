package ui.elements;

import javax.swing.*;


// Represents a generic button for controlling playback in the audio
// player.

public class PlaybackButton extends JButton {

    public PlaybackButton(String buttonType) {
        super(new ImageIcon("././data/" + buttonType + ".png"));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        // Super temporary, just to confirm when button is pressed
        this.setPressedIcon(new ImageIcon("././data/smallLogo.png"));

        // TODO: Create and add pressed state for buttons
        //this.setPressedIcon(new ImageIcon("././data/" + buttonType + "Pressed" + ".png"));
    }


}
