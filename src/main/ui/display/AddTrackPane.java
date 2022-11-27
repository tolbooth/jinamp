package ui.display;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


// Represents the "add track pane" for the graphical user interface of the player
// Allows users to assign track and artist names to all playable files in
// the data folder
public class AddTrackPane extends JPanel implements SeesFiles, ListSelectionListener, ActionListener, FocusListener {
    private static JFrame frame;
    private Core core;
    private TrackBuilder trackBuilder;
    private String currentSelectedTrackFileName;
    private JPanel mainPanel;
    private JPanel lowerPanel;
    private JPanel upperPanel;
    private JScrollPane fileScrollPane;
    private JScrollPane addedScrollPane;
    private JTextField trackNameField;
    private JTextField artistNameField;
    private JButton addTrackButton;
    private DefaultListModel<String> fileNamesList;
    private DefaultListModel<String> addedFilesList;
    private JList<String> fileNames;
    private JList<String> addedFiles;


    // EFFECTS: Instantiates the add track pane with provided core
    public AddTrackPane(Core core) {
        this.core = core;
        trackBuilder = new TrackBuilder();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scrollPaneSetup(core.getUnassignedFiles());
        textFieldSetup();
        buttonSetup();
        lowerPanelSetup();
        upperPanelSetup();

        mainPanel = new JPanel();
        mainPanel.add(upperPanel);
        mainPanel.add(lowerPanel);
    }

    // EFFECTS: Makes elements of the add track pane visible
    // MODIFIES: this
    public void startGUI() {
        //Create and set up the window.
        frame = new JFrame("Add Tracks From File To Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                core.getFileHandler().write(core.getLibrary());
            }
        });

        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel);
        //Display the window.
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // EFFECTS: instantiates the button and adds listener
    // MODIFIES: this
    private void buttonSetup() {
        addTrackButton = new JButton("Add Track");
        addTrackButton.addActionListener(this);
    }

    // EFFECTS: sets up the text fields with focus listeners to display
    // placeholder text when unselected
    // MODIFIES: this
    private void textFieldSetup() {
        trackNameField = new JTextField();
        artistNameField = new JTextField();

        trackNameField.addFocusListener(this);
        artistNameField.addFocusListener(this);

        trackNameField.setText("Track Name");
        artistNameField.setText("Artist Name");
    }

    // EFFECTS: instantiates the scroll panes and jlists for track assignment
    // MODIFIES: this
    private void scrollPaneSetup(ArrayList<String> playableFiles) {
        currentSelectedTrackFileName = "";
        fileNamesList = new DefaultListModel<>();
        populateListModel(fileNamesList, playableFiles);

        fileNames = new JList<>(fileNamesList);
        fileNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileNames.setSelectedIndex(0);
        fileNames.addListSelectionListener(this);

        fileScrollPane = new JScrollPane(fileNames);

        addedFilesList = new DefaultListModel<>();
        addedFiles = new JList<>(addedFilesList);
        addedFiles.setEnabled(false);
        addedScrollPane = new JScrollPane(addedFiles);
    }

    // EFFECTS: adds elements to lower panel
    // MODIFIES: this
    private void lowerPanelSetup() {
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel,
                BoxLayout.PAGE_AXIS));
        lowerPanel.add(artistNameField);
        lowerPanel.add(trackNameField);
        lowerPanel.add(addTrackButton);
    }

    // EFFECTS: adds elements to upper panel
    // MODIFIES: this
    private void upperPanelSetup() {
        upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.LINE_AXIS));
        upperPanel.add(fileScrollPane);
        upperPanel.add(addedScrollPane);
    }

    // EFFECTS: takes the given list model and populates it with values from an arraylist
    private void populateListModel(DefaultListModel<String> listModel, ArrayList<String> list) {
        for (String file : list) {
            listModel.addElement(file);
        }
    }

    // EFFECTS: Displays error window if you try and add a track with nothing selected
    private void selectedError() {
        JOptionPane.showMessageDialog(this, "Please Select a File");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        if (list.getSelectedIndex() != -1) {
            currentSelectedTrackFileName = fileNamesList.get(list.getSelectedIndex());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(addTrackButton)) {
                String fileName = fileNames.getSelectedValue().toString();

                // Construct track object from input data fields and selected file
                Track track = trackBuilder.buildTrack(fileName,
                        trackNameField.getText(), artistNameField.getText());

                // Add track to library of Core passed in
                core.getLibrary().add(track);

                // "move" the track to other window in toString format
                addedFilesList.addElement(track.toString());
                fileNamesList.remove(fileNames.getSelectedIndex());
                // This is our completion condition -- if all filenames that were not
                // associated are now associated.
                if (fileNamesList.isEmpty()) {
                    core.getFileHandler().write(core.getLibrary());
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        } catch (NullPointerException n) {
            selectedError();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField tf = (JTextField) (e.getSource());
        if (tf.getText().isEmpty()) {
            if (e.getSource().equals(trackNameField)) {
                tf.setText("Track Name");
            } else if (e.getSource().equals(artistNameField)) {
                tf.setText("Artist Name");
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField tf = (JTextField) (e.getSource());
        if (tf.getText().equals("Track Name") || tf.getText().equals("Artist Name")) {
            tf.setText("");
        }
    }
}
