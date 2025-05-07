package ui.tabs;

import javax.swing.*;

import model.Dictionary;
import model.Term;
import persistence.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import ui.DictionaryUI;

// A tab where users can see all terms, all favourite terms, and save their dictionary
public class HomeTab extends Tab {
    private static final String STORE_FILE = "./data/myDictionary.json";

    private JsonWriter jsonWriter;
    private JLabel homeTabText;
    private JLabel imageAsLabel;
    private JPanel termsPanel;
    private JPanel termComponentPanel;
    private JScrollPane dictionaryScroll;
    private ImageIcon favImage;

    // REQUIRES: DictionaryUI controller that holds this tab
    // MODIFIES: this
    // EFFECTS: creates home tab that allows users to view all terms or favourite terms in a scroll panel, and save
    //          upon start up, all terms are displayed if a previous dictionary is loaded
    public HomeTab(DictionaryUI controller, Dictionary dict) {
        super(controller, dict);
        setLayout(new BorderLayout(5, 10));
        setBackground(new Color(162, 199, 219));

        jsonWriter = new JsonWriter(STORE_FILE);

        placeText();
        placeTermsPanel();
        placeScrollPane();
        placeButtons();
        displayTerms();
    }

    // MODIFIES: this
    // EFFECTS: sets up the label displaying status text on the tab
    private void placeText() {
        homeTabText = new JLabel("There are no terms in your dictionary", JLabel.CENTER);
        homeTabText = new JLabel("Viewing all terms", JLabel.CENTER);
        homeTabText.setSize(WIDTH, HEIGHT / 4);
        homeTabText.setFont(new Font("Arial", Font.BOLD, 14));
        add(homeTabText, BorderLayout.PAGE_START);
    }
    
    // MODIFIES: this
    // EFFECTS: initializes the panel that holds dictionary terms
    private void placeTermsPanel() {
        termsPanel = new JPanel();
        termsPanel.setLayout(new BoxLayout(termsPanel, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a scroll pane to the UI to hold dictionary terms
    private void placeScrollPane() {        
        dictionaryScroll = new JScrollPane(termsPanel);
        dictionaryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dictionaryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(dictionaryScroll, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to display all terms, favourite terms, or save the dictionary, and adds ActionListeners
    private void placeButtons() {        
        JButton b1 = new JButton("View All Terms");
        JButton b2 = new JButton("View Favourite Terms");
        JButton b3 = new JButton("Save Dictionary");
        JPanel buttons = new JPanel();

        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.setSize(WIDTH, HEIGHT / 6);
        buttons.setBackground(new Color(162, 199, 219));
        add(buttons, BorderLayout.PAGE_END);

        b1.addActionListener(e -> displayTerms());

        b2.addActionListener(e -> displayFavouriteTerms());

        b3.addActionListener(e -> saveDictionary());
    }

    // MODIFIES: this
    // EFFECTS: displays all terms in the dictionary and updates the status text
    private void displayTerms() {
        resetScrollPanel(); 

        ArrayList<Term> terms = dict.getTerms();

        if (terms.isEmpty()) {
            homeTabText.setText("There are no terms in your dictionary");
        } else {
            homeTabText.setText("Viewing all terms.");
            for (int i = 0; i < terms.size(); i++) {
                createTermPanel(terms.get(i));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all favourite terms in the dictionary and updates the status text
    private void displayFavouriteTerms() {
        resetScrollPanel(); 
        
        ArrayList<Term> favs = dict.filterFavTerms();

        if (favs.isEmpty()) {
            homeTabText.setText("There are no terms in your dictionary");
        } else {
            homeTabText.setText("Viewing favourited terms.");
            for (int i = 0; i < favs.size(); i++) {
                createTermPanel(favs.get(i));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the scroll panel
    private void resetScrollPanel() {
        termsPanel.removeAll();
        termsPanel.repaint();
        termsPanel.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: creates a panel to display a term's details and adds it to the terms panel
    //          if the term is a favourite, a star image is added to the term's panel
    private void createTermPanel(Term t) {
        termComponentPanel = new JPanel();
        termComponentPanel.setLayout(new BoxLayout(termComponentPanel, BoxLayout.X_AXIS));
        termComponentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameAndDash = new JLabel(t.getName() + "  -  ", JLabel.LEFT);
        nameAndDash.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        JLabel def = new JLabel(t.getTranslation(), JLabel.LEFT);
        def.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        termComponentPanel.add(nameAndDash);
        termComponentPanel.add(def);

        if (t.getFavourite()) {
            JLabel spacer = new JLabel("   ", JLabel.LEFT);
            termComponentPanel.add(spacer);
            favImage = new ImageIcon("./data/images/favTerm.png");
            imageAsLabel = new JLabel(favImage);
            termComponentPanel.add(imageAsLabel);
        }

        termsPanel.add(termComponentPanel);
    }

    // MODIFIES: this
    // EFFECTS: saves the dictionary to file and updates text label to inform user of success
    private void saveDictionary() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(dict);
            jsonWriter.closeWriter();
            homeTabText.setText("Saved " + dict.getName() + ".");
        } catch (FileNotFoundException e) {
            homeTabText.setText("Unable to write to file: " + STORE_FILE);
        }
    }
}
