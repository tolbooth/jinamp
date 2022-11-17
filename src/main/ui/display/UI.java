package ui.display;

import model.Library;
import model.Player;
import ui.elements.PlaybackButton;
import ui.interaction.UserFunctions;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
    private JPanel panel1;
    private JLabel backgroundImg;
    private JButton button;

    private static final String PLAY = "PlayButton";
    private static final String PAUSE = "PauseButton";
    private static final String STOP = "StopButton";
    private static final String SKIP_F = "SkipForwardButton";
    private static final String SKIP_B = "SkipBackButton";


    public UI(Library library, Player player, UserFunctions userFunctions) {

    }

    public UI() {
        setTitle("JinAmp");
        setResizable(false);
        setSize(600, 600);

        button = new PlaybackButton(PLAY);

        panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.setLayout(new FlowLayout());

        panel1.add(button);

        backgroundImg = new JLabel();
        backgroundImg.setIcon(new ImageIcon("././data/background.png"));
        backgroundImg.setLayout(new BorderLayout());

        setIconImage(new ImageIcon("././data/smallLogo.png").getImage());

        backgroundImg.add(panel1);
        add(backgroundImg);
    }

    private void initializeButtons() {

    }

    private void initializeFrames() {

    }


}
