package ui.display;

import model.Core;
import model.EventLog;
import model.Event;
import model.Track;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;


// Represents the main Graphical User Interface that coordinates Player functions,
// along with library and playlist management.

public class UI extends JFrame {
    private Core core;

    private Timer timer;

    private static JFrame frame;
    private JSplitPane mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane libraryPane;
    private JScrollPane currentPlaylistPane;
    private JPanel trackInfoPane;
    private JPanel buttonPane;

    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton skipFwButton;
    private JButton skipBwButton;

    private JLabel trackInfoLabel;

    private DefaultListModel<String> libraryListModel;
    private JList<String> libraryList;
    private DefaultListModel<String> currentPlaylistModel;
    private JList<String> currentPlaylistList;
    private HashMap<String, Track> libraryHash;

    private static final String PLAY = "./././data/graphics/PlayButtonTransparent.png";
    private static final String PAUSE = "./././data/graphics/PauseButtonTransparent.png";
    private static final String STOP = "./././data/graphics/StopButtonTransparent.png";
    private static final String SKIP_F = "./././data/graphics/SkipFwButtonTransparent.png";
    private static final String SKIP_B = "./././data/graphics/SkipBwButtonTransparent.png";

    // EFFECTS: Instantiates a new UI object, creating and organizing components and functionality
    public UI(Core core) {
        this.core = core;
        libraryHash = new HashMap<>();
        for (Track t : this.core.getLibrary().getTrackList()) {
            libraryHash.put(t.toString(), t);
        }

        setupLibraryPane();
        setupPlaylistPane();
        setupButtons();

        trackInfoLabel = new JLabel("", SwingConstants.CENTER);
        updateLabel();

        setupTrackInfoPane();
        setupPanels();

    }

    // EFFECTS: Starts all functionality required for GUI and makes it visible
    // MODIFIES: this

    public void startGUI() {
        if (core.checkNewTracks()) {
            AddTrackPane atPane = new AddTrackPane(core);
            atPane.startGUI();
        }

        frameSetup();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                core.getFileHandler().write(core.getPlayer());
                timer.stop();
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next);
                }
            }
        });

        setupTimer();

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        mainPanel.setDividerLocation(0.50);
    }

    private void frameSetup() {
        frame = new JFrame("JinAmp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));
    }

    // EFFECTS: Instantiates a timer with 10 ms delay to constantly update
    // track display label
    // MODIFIES: this
    private void setupTimer() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.getPlayer().updateCurrentPosition();
                updateLabel();
            }
        });
        timer.start();
    }

    // EFFECTS: Performs setup on all playback buttons, and assigns their listeners
    // MODIFIES: this
    private void setupButtons() {
        buttonPane = new JPanel();
        buttonPane.setPreferredSize(new Dimension(300, 100));
        buttonPane.setLayout(new GridLayout(1, 5));

        skipBwButton = new JButton();
        skipBwButton.addActionListener(new PlaybackButtonListener());
        skipBwButton.setIcon(new ImageIcon(SKIP_B));
        buttonPane.add(skipBwButton);

        playButton = new JButton();
        playButton.addActionListener(new PlaybackButtonListener());
        playButton.setIcon(new ImageIcon(PLAY));
        buttonPane.add(playButton);

        pauseButton = new JButton();
        pauseButton.addActionListener(new PlaybackButtonListener());
        pauseButton.setIcon(new ImageIcon(PAUSE));
        buttonPane.add(pauseButton);

        stopButton = new JButton();
        stopButton.addActionListener(new PlaybackButtonListener());
        stopButton.setIcon(new ImageIcon(STOP));
        buttonPane.add(stopButton);

        skipFwButton = new JButton();
        skipFwButton.addActionListener(new PlaybackButtonListener());
        skipFwButton.setIcon(new ImageIcon(SKIP_F));
        buttonPane.add(skipFwButton);
    }

    // EFFECTS: Performs setup on the major panels present in the UI
    // MODIFIES: this
    private void setupPanels() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.add(libraryPane);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.add(trackInfoPane);
        rightPanel.add(currentPlaylistPane);

        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                rightPanel, leftPanel);
    }


    // EFFECTS: Performs setup on the library pane, including
    // setting up the Library List model and JList
    // MODIFIES: this
    private void setupLibraryPane() {
        libraryListModel = new DefaultListModel<>();
        for (Track t : this.core.getLibrary().getTrackList()) {
            libraryListModel.addElement(t.toString());
        }
        // Note: We actually only want to copy from library list to queue/playlist list
        libraryList = new JList<>(libraryListModel);
        libraryList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        libraryList.setBorder(BorderFactory.createTitledBorder("Library"));
        libraryPane = new JScrollPane(libraryList);
        libraryPane.setPreferredSize(new Dimension(300, 600));
        libraryList.setDragEnabled(true);
        libraryList.setTransferHandler(new ListTransferHandler());
        libraryList.addMouseListener(getLibraryMouseListener());
    }

    private MouseAdapter getLibraryMouseListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                // listen for a double click
                if (event.getClickCount() == 2) {
                    int i = list.locationToIndex(event.getPoint());
                    // add to playlist pane and update playlist
                    currentPlaylistModel.addElement(libraryListModel.getElementAt(i));
                    updatePlaylist();
                }
            }
        };
    }

    // EFFECTS: Performs setup on the playlist pane, including
    // setting up the Playlist List model and JList
    // MODIFIES: this
    private void setupPlaylistPane() {
        currentPlaylistModel = new DefaultListModel<>();
        for (Track t : this.core.getPlayer().getCurrentPlaylist().getTrackList()) {
            currentPlaylistModel.addElement(t.toString());
        }
        currentPlaylistModel.addListDataListener(new PlaylistListDataListener());
        currentPlaylistList = new JList<>(currentPlaylistModel);
        currentPlaylistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currentPlaylistList.setBorder(BorderFactory.createTitledBorder("Playlist"));
        // Don't actually want to be able to drag anything in this list
        currentPlaylistList.setDragEnabled(false);
        currentPlaylistList.setTransferHandler(new ListTransferHandler());
        currentPlaylistList.addMouseListener(getPlaylistMouseListener());
        currentPlaylistList.addKeyListener(new ListKeyListener());
        currentPlaylistPane = new JScrollPane(currentPlaylistList);
        currentPlaylistPane.setPreferredSize(new Dimension(300, 400));
    }

    private MouseAdapter getPlaylistMouseListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                // listen for a double click
                if (event.getClickCount() == 2) {
                    int i = list.locationToIndex(event.getPoint());
                    core.getPlayer().stopTrack();
                    core.getPlayer().playTrack(i);
                    updateLabel();
                }
            }
        };
    }

    // EFFECTS: Performs setup on the track info pane
    // MODIFIES: this
    private void setupTrackInfoPane() {
        trackInfoPane = new JPanel();
        trackInfoPane.setPreferredSize(new Dimension(300, 200));
        JPanel trackTextPane = new JPanel();
        trackTextPane.setBorder(BorderFactory.createLoweredBevelBorder());
        trackTextPane.setBackground(new Color(255, 255, 255));
        trackInfoPane.add(buttonPane);
        trackInfoPane.setLayout(new GridLayout(2, 1));
        trackTextPane.add(trackInfoLabel);
        trackInfoPane.add(trackTextPane);
        trackInfoPane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        trackInfoPane.add(buttonPane);
    }

    // EFFECTS: Clears the current playlist and updates it with whatever is being displayed a current playlist
    // MODIFIES: this
    private void updatePlaylist() {
        core.getPlayer().getCurrentPlaylist().clearTracklist();
        for (int i = 0; i < currentPlaylistModel.getSize(); i++) {
            core.getPlayer().getCurrentPlaylist().addTrack(libraryHash.get(currentPlaylistModel.getElementAt(i)));
        }
    }

    // EFFECTS: Updates the track display JLabel with current track and time
    // MODIFIES: this
    private void updateLabel() {
        String displayString = "<html>" + core.getPlayer().getCurrentTrack().toString()
                + "<br>" + core.getPlayer().timeToString() + "</html>";
        trackInfoLabel.setText(displayString);
    }

    // Represents a data listener for playlist list. Updates display and
    // Player playlist whenever something changes
    private class PlaylistListDataListener implements ListDataListener {
        @Override
        public void intervalAdded(ListDataEvent e) {
            updatePlaylist();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            updatePlaylist();
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            updatePlaylist();
        }
    }

    // Represents a playback button listener
    private class PlaybackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Execute the function related to the button
            if (e.getSource().equals(playButton)) {
                core.getPlayer().playTrack();
            } else if (e.getSource().equals(pauseButton)) {
                core.getPlayer().pauseTrack();
            } else if (e.getSource().equals(stopButton)) {
                core.getPlayer().stopTrack();
            } else if (e.getSource().equals(skipFwButton)) {
                core.getPlayer().nextTrack();
                currentPlaylistList.setSelectedIndex(currentPlaylistList.getSelectedIndex() + 1);
            } else if (e.getSource().equals(skipBwButton)) {
                core.getPlayer().previousTrack();
                currentPlaylistList.setSelectedIndex(currentPlaylistList.getSelectedIndex() - 1);
            }
        }
    }

    // Represents a key listener for the playlist list
    private class ListKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Get the key which triggered the event
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DELETE :
                    // Remove the element from playlist
                    currentPlaylistModel.removeElementAt(currentPlaylistList.getSelectedIndex());
                    break;
                case KeyEvent.VK_ENTER :
                    // Start playing the element
                    core.getPlayer().playTrack(currentPlaylistList.getSelectedIndex());
                    updateLabel();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}