package ui.tabs;

import model.Dictionary;
import model.Term;
import ui.DictionaryUI;

import javax.swing.*;
import java.awt.*;

// A tab where users can search for a term and see all its details, toggle favourite, or remove it
public class SearchTab extends Tab {
    private JPanel searchPanel;
    private JPanel displayPanel;
    private JPanel termCharacteristics;
    private JPanel optButtons;
    private JLabel searchTabText;
    private JLabel displayText;
    private JLabel translText;
    private JLabel termTransl;
    private JLabel exText;
    private JLabel termEx;
    private JLabel notesText;
    private JLabel termNotes; 
    private JLabel favText;
    private JTextField searchField;
    private Term currentTerm;

    // REQUIRES: DictionaryUI controller that holds this tab
    // MODIFIES: this
    // EFFECTS: creates search tab where users can find a term and view all its details, change the favourite stauts,
    //          or remove the term from the dictionary
    public SearchTab(DictionaryUI controller, Dictionary dict) {
        super(controller, dict);
        setLayout(new BorderLayout(5, 10));
        setBackground(new Color(162, 199, 219));

        placeDisplayPanel();
        placeSearchPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the display panel for showing the term details
    private void placeDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout(5, 10));
        displayPanel.setBackground(new Color(162, 199, 219));

        displayText = new JLabel("", JLabel.CENTER);
        displayText.setFont(new Font("Arial", Font.BOLD, 14));
        displayPanel.add(displayText, BorderLayout.PAGE_START);

        termCharacteristics = new JPanel();
        termCharacteristics.setLayout(new GridLayout(7, 1));
        termCharacteristics.setBackground(new Color(162, 199, 219));

        initializeAndAddLabels();
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds labels for term characteristics to the display panel
    private void initializeAndAddLabels() {
        translText = new JLabel("", JLabel.CENTER);
        translText.setFont(new Font("Arial", Font.BOLD, 14));
        termTransl = new JLabel("", JLabel.CENTER);
        exText = new JLabel("", JLabel.CENTER);
        exText.setFont(new Font("Arial", Font.BOLD, 14));
        termEx = new JLabel("", JLabel.CENTER);
        notesText = new JLabel("", JLabel.CENTER);
        notesText.setFont(new Font("Arial", Font.BOLD, 14));
        termNotes = new JLabel("", JLabel.CENTER);
        favText = new JLabel("", JLabel.CENTER);
        favText.setFont(new Font("Arial", Font.BOLD, 14));

        termCharacteristics.add(translText);
        termCharacteristics.add(termTransl);
        termCharacteristics.add(exText);
        termCharacteristics.add(termEx);
        termCharacteristics.add(notesText);
        termCharacteristics.add(termNotes);
        termCharacteristics.add(favText);

        displayPanel.add(termCharacteristics);
    }

    // MODIFIES: this
    // EFFECTS: sets up the search panel with a text field and button
    private void placeSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(5, 5));
        searchPanel.setBackground(new Color(162, 199, 219));
        
        searchTabText = new JLabel("Enter the name of the term you are looking for and press search.", JLabel.CENTER);
        searchTabText.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(searchTabText, BorderLayout.PAGE_START);
        
        searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        JPanel intermediary = new JPanel();
        intermediary.setBackground(new Color(162, 199, 219));
        intermediary.add(searchButton);
        searchPanel.add(intermediary, BorderLayout.PAGE_END);

        searchButton.addActionListener(e -> handleSearch());

        add(searchPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: handles the search action, updating the display panel if the term is found;
    //          showing an error message and clearing the details otherwise
    private void handleSearch() {
        String name = searchField.getText();
        currentTerm = dict.findTerm(name);

        if (currentTerm != null) {
            searchTabText.setText("Term Found!");
            searchField.setText("");
            updateDisplayPanel();
        } else {
            searchTabText.setText("Invalid input. There is no term in your dictionary with that name.");
            searchField.setText("");
            clearPastDisplay();
            optButtons.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the display panel to show details and action options for the searched for term
    private void updateDisplayPanel() {
        displayDetails();
        displayOptionButtons();

        add(displayPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: updates the display panel with details of the specified term
    private void displayDetails() {
        clearPastDisplay();
        
        displayText.setText(currentTerm.getName() + " (" + currentTerm.getGType() + ")");
        translText.setText("Translation");
        termTransl.setText(currentTerm.getTranslation());
        exText.setText("Example Sentence");
        termEx.setText(currentTerm.getExample());
        notesText.setText("Notes");
        termNotes.setText(textBasedOnHasNotes());
        if (currentTerm.getFavourite()) {
            favText.setText("** FAVOURITE WORD **");
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all displayed term details from the display panel
    private void clearPastDisplay() {
        displayText.setText("");
        translText.setText("");
        termTransl.setText("");
        exText.setText("");
        termEx.setText("");
        notesText.setText("");
        termNotes.setText("");
        favText.setText("");
    }

    // EFFECTS: returns the notes of the specified term or "N/A" if there are no notes
    private String textBasedOnHasNotes() {
        if (currentTerm.getNotes().equals("")) {
            return "N/A";
        } else {
            return currentTerm.getNotes();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays buttons to favourite/unfavourite or remove the specified term
    private void displayOptionButtons() {
        JButton b1 = new JButton("Favourite/Unfavourite Term");
        JButton b2 = new JButton("Remove Term");
        optButtons = new JPanel();

        optButtons.add(b1);
        optButtons.add(b2);
        optButtons.setSize(WIDTH, HEIGHT / 6);
        optButtons.setBackground(new Color(162, 199, 219));
        add(optButtons, BorderLayout.PAGE_END);

        b1.addActionListener(e -> handleFavouriteButton());

        b2.addActionListener(e -> handleRemoveButton());
    }

    // MODIFIES: this, currentTerm
    // EFFECTS: toggles the favourite status of the specified term and updates term and the display accordingly
    private void handleFavouriteButton() {
        if (currentTerm.getFavourite()) {
            currentTerm.setUnfavourite();
            displayText.setText("This term has been unfavourited.");
            favText.setText("");
        } else {
            currentTerm.setFavourite();
            displayText.setText("This term has been favourited!");
            favText.setText("** FAVOURITE WORD **");
        }
    }

    // MODIFIES: this, dict
    // EFFECTS: removes the specified term from the dictionary and updates the display panel
    private void handleRemoveButton() {
        dict.deleteTerm(currentTerm.getName());
        clearPastDisplay();
        displayText.setText("This term has been removed. Press view all terms in the Home Tab to see this update.");
    }
}
