package ui.display;

import model.Core;
import model.Track;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class UI extends JFrame {
    private Core core;


    private static JFrame frame;
    private JSplitPane mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane libraryPane;
    private JScrollPane currentPlaylistPane;

    private DefaultListModel<String> libraryListModel;
    private JList<String> libraryList;
    private DefaultListModel<String> currentPlaylistModel;
    private JList<String> currentPlaylistList;
    private HashMap<String, Track> libraryHash;

    private static final String PLAY = "PlayButton";
    private static final String PAUSE = "PauseButton";
    private static final String STOP = "StopButton";
    private static final String SKIP_F = "SkipForwardButton";
    private static final String SKIP_B = "SkipBackButton";


    public UI(Core core) {
        this.core = core;

//        for (Track t : this.core.getLibrary().getTrackList()) {
//            libraryHash.put(t.toString(), t);
//        }
//
//        setupLibraryList();
//        setupPlaylistList();
//
//        leftPanel = new JPanel();
//        leftPanel.add(libraryPane);
//        rightPanel = new JPanel();
//        rightPanel.add(currentPlaylistPane);
//
//
//        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//                leftPanel, rightPanel);

    }

    public void startGUI() {
        if (core.checkNewTracks()) {
            AddTrackPane atPane = new AddTrackPane(core);
            atPane.startGUI();
        }

//        frame = new JFrame("JinAmp");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.setPreferredSize(new Dimension(600, 600));
//
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                core.getFileHandler().write(core.getPlayer());
//            }
//        });
//
//        JLabel contentPane = new JLabel();
//        contentPane.setIcon(new ImageIcon("./././data/background1.png"));
//        contentPane.setLayout(new BorderLayout());
//        contentPane.add(mainPanel);
//
//        frame.setContentPane(contentPane);
//        frame.setResizable(false);
//        frame.setVisible(true);
    }

    private void setupButtons() {

    }

    private void setupFrames() {

    }

    private void setupLibraryList() {
        libraryListModel = new DefaultListModel<>();
        for (Track t : this.core.getLibrary().getTrackList()) {
            libraryListModel.addElement(t.toString());
        }
        // Note: We actually only want to copy from library list to queue/playlist list
        libraryList = new JList<>(libraryListModel);
        libraryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        libraryList.setSelectedIndex(0);
        libraryPane = new JScrollPane(libraryList);
        libraryPane.setPreferredSize(new Dimension(286, 590));
        libraryList.setDragEnabled(true);
        libraryList.setTransferHandler(new ListTransferHandler());
    }

    private void setupPlaylistList() {
        currentPlaylistModel = new DefaultListModel<>();
        for (Track t : this.core.getPlayer().getCurrentPlaylist().getTrackList()) {
            currentPlaylistModel.addElement(t.toString());
        }

        currentPlaylistModel.addListDataListener(new PlaylistListDataListener());
        currentPlaylistList = new JList<>(currentPlaylistModel);
        currentPlaylistList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        currentPlaylistList.setSelectedIndex(0);
        // Don't actually want to be able to drag anything in this list
        currentPlaylistList.setDragEnabled(false);
        currentPlaylistList.setTransferHandler(new ListTransferHandler());
        currentPlaylistPane = new JScrollPane(currentPlaylistList);
        currentPlaylistPane.setPreferredSize(new Dimension(286,386));
    }

    // Clears the current playlist and updates it with whatever is being displayed a current playlist
    private void updatePlaylist() {
        core.getPlayer().getCurrentPlaylist().getTrackList().clear();
        for (int i = 0; i < currentPlaylistModel.getSize(); i++) {
            core.getPlayer().getCurrentPlaylist().getTrackList()
                    .add(libraryHash.get(currentPlaylistModel.getElementAt(i)));
        }
    }

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
}
