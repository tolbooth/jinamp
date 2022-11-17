package ui.display;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AddTrackPane extends JPanel implements SeesFiles, ListSelectionListener, ActionListener {
    private static JFrame frame;
    private ArrayList<Track> tracks;
    private TrackBuilder trackBuilder;
    private String currentSelectedTrackFileName;
    private JPanel mainPanel;
    private JTextField trackNameField;
    private JTextField artistNameField;
    private JButton addTrackButton;
    private DefaultListModel<String> fileNamesList;
    private DefaultListModel<String> addedFilesList;
    private JList<String> fileNames;
    private JList<String> addedFiles;


    // TODO: Finish implementation of this and test!
    // TODO: Attempt to organize GUI elements
    // TODO: Break this into sub methods
    public AddTrackPane(ArrayList<String> playableFiles, ArrayList<Track> trackList) {
        tracks = trackList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        currentSelectedTrackFileName = "";
        fileNamesList = new DefaultListModel<>();
        populateListModel(fileNamesList, playableFiles);

        fileNames = new JList<>(fileNamesList);
        fileNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileNames.setSelectedIndex(0);
        fileNames.addListSelectionListener(this);

        JScrollPane fileScrollPane = new JScrollPane(fileNames);

        addedFilesList = new DefaultListModel<>();
        addedFiles = new JList<>(addedFilesList);
        addedFiles.setEnabled(false);
        JScrollPane addedScrollPane = new JScrollPane(addedFiles);

        trackNameField = new JTextField();
        artistNameField = new JTextField();
        trackNameField.setText("Track Name");
        artistNameField.setText("Artist Name");

        addTrackButton = new JButton("Add Track");
        addTrackButton.addActionListener(this);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel,
                BoxLayout.PAGE_AXIS));
        lowerPanel.add(trackNameField);
        lowerPanel.add(artistNameField);
        lowerPanel.add(addTrackButton);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.LINE_AXIS));

        JPanel mainPanel = new JPanel();

        upperPanel.add(fileScrollPane);
        upperPanel.add(addedScrollPane);

        mainPanel.add(upperPanel);
        mainPanel.add(lowerPanel);
    }

    public void startGUI() {
        //Create and set up the window.
        frame = new JFrame("Add Tracks From File To Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void populateListModel(DefaultListModel<String> listModel, ArrayList<String> list) {
        for (String file : list) {
            listModel.addElement(file);
        }
    }

    // EFFECTS: Displays error window if you try and add a track with nothing selected
    private void selectedError() {
        JFrame errorFrame = new JFrame();
        errorFrame.setSize(200, 200);
        errorFrame.setVisible(true);
        JOptionPane.showMessageDialog(errorFrame, "Please Select a File");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        currentSelectedTrackFileName = fileNamesList.get(list.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(addTrackButton)) {
                // Construct track object from input data fields and selected file
                Track track = trackBuilder.buildTrack(fileNames.getSelectedValue().toString(),
                        trackNameField.getText(), artistNameField.getText());
                // Add track to list passed in
                tracks.add(track);
                // "move" the track to other window in toString format
                addedFilesList.addElement(track.toString());
                fileNamesList.removeElementAt(fileNames.getSelectedIndex());
                // This is our completion condition -- if all filenames that were not
                // associated are now associated.
                if (fileNamesList.isEmpty()) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        } catch (NullPointerException n) {
            selectedError();
        }
    }
}
