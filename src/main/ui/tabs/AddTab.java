package ui.tabs;

import model.Dictionary;
import model.Term;
import ui.DictionaryUI;

import javax.swing.*;
import java.awt.*;

// A tab where users can see add a term to their dictionary with each of the fields custom
public class AddTab extends Tab {
    private static final String SPACER = "   ";
    
    private JPanel addPanel;
    private JLabel addTabText;
    private JTextField termField;
    private JTextField typeField;
    private JTextField translationField;
    private JTextField exampleField;
    private JTextArea notesArea;
    private JCheckBox favouriteCheck;

    //REQUIRES: DictionaryUI controller that holds this tab
    //EFFECTS: creates a tab that allows users to add new terms to the dictionary
    public AddTab(DictionaryUI controller, Dictionary dict) {
        super(controller, dict);
        setLayout(new BorderLayout(5, 10));
        setBackground(new Color(162, 199, 219));

        placeText();
        placeAddPanel();
        placeSubmitButton();
    }

    // MODIFIES: this
    // EFFECTS: sets up a label for displaying status messages
    private void placeText() {
        addTabText = new JLabel("", JLabel.CENTER);
        addTabText.setSize(WIDTH, HEIGHT / 4);
        addTabText.setFont(new Font("Arial", Font.BOLD, 14));
        add(addTabText, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds the panel for user input fields
    private void placeAddPanel() {
        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(6, 2));
        addPanel.setBackground(new Color(162, 199, 219));
        placePromptsAndFields();

        add(addPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds text fields and labels to the add panel
    private void placePromptsAndFields() {
        initializeFields();
        addPanel.add(new JLabel(SPACER + "Name", JLabel.LEFT));
        addPanel.add(termField);
        addPanel.add(new JLabel(SPACER + "Type (e.g. noun)", JLabel.LEFT));
        addPanel.add(typeField);
        addPanel.add(new JLabel(SPACER + "Translation", JLabel.LEFT));
        addPanel.add(translationField);
        addPanel.add(new JLabel(SPACER + "Example Sentence", JLabel.LEFT));
        addPanel.add(exampleField);
        addPanel.add(new JLabel(SPACER + "Notes (optional)", JLabel.LEFT));
        addPanel.add(new JScrollPane(notesArea));
        addPanel.add(new JLabel(SPACER + "Is this term a favourite?", JLabel.LEFT));
        addPanel.add(favouriteCheck);
    }

    // MODIFIES: this
    // EFFECTS: initializes the input fields and checkbox
    private void initializeFields() {
        termField = new JTextField();
        typeField = new JTextField();
        translationField = new JTextField();
        exampleField = new JTextField();
        notesArea = new JTextArea(5, 20);
        favouriteCheck = new JCheckBox();
    }

    // MODIFIES: this
    // EFFECTS: adds a submit button for adding a new term and assigns an ActionListener
    private void placeSubmitButton() {
        JButton b1 = new JButton("Add Term");

        add(b1, BorderLayout.PAGE_END);

        b1.addActionListener(e -> handleSubmit());
    }

    // MODIFIES: this, Dictionary (through the controller)
    // EFFECTS: creates a new term from user input and adds it to the dictionary if all required fields are filled;
    //          displays a success or error message accordingly
    private void handleSubmit() {
        String name = termField.getText().trim();
        String type = typeField.getText().trim();
        String translation = translationField.getText().trim();
        String example = exampleField.getText().trim();
        String notes = notesArea.getText().trim();
        boolean isFavourite = favouriteCheck.isSelected();

        if (checkNotEmpty(name, type, translation, example)) {
            Term newTerm = createTerm(name, type, translation, example, notes, isFavourite);
            dict.addTerm(newTerm);
            if (isFavourite) {
                newTerm.setFavourite();
            }
            addTabText.setText("Term added successfully! Press the view all terms button in the Home Tab to update.");
            clearInputFields();
        } else {
            addTabText.setText("Invalid input. The first four fields must not be empty to create a new term.");
        }
    }

    // EFFECTS: returns true if none of the required fields are empty
    private boolean checkNotEmpty(String name, String type, String translation, String example) {
        return !name.isEmpty() && !type.isEmpty() && !translation.isEmpty() && !example.isEmpty();
    }

    // EFFECTS: creates and returns a new Term object with the given attributes
    private Term createTerm(String name, String type, String translation, String example, String notes, 
                            boolean isFavourite) {
        Term newTerm = new Term(name, type, translation, example);
        newTerm.setNotes(notes);
        return newTerm;
    }

    // MODIFIES: this
    // EFFECTS: clears all input fields and resets the favourite checkbox
    private void clearInputFields() {
        termField.setText("");
        typeField.setText("");
        translationField.setText("");
        exampleField.setText("");
        notesArea.setText("");
        favouriteCheck.setSelected(false);
    }
}
