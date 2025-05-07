package ui;

import model.Dictionary;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// A load screen panel with text, buttons, fields, and an image that allows users to load or create a new dictionary
public class LoadScreen extends JPanel {
    public static final String WELCOME_MSG_1 = "Welcome to your language learning assistant, LingoLearner!";
    public static final String WELCOME_MSG_2 = "Would you like to create a new dictionary, "
                                                + "or load the last saved dictionary?";
    public static final String ENTER_NAME_MSG1 = "What would you like to name your personal dictionary?";
    public static final String ENTER_NAME_MSG2 = "Press <Enter> when done";
    private static final String STORE_FILE = "./data/myDictionary.json";
    private final WelcomeUI controller;

    private JsonReader jsonReader;
    private JPanel imagePanel;
    private JPanel promptPanel;
    private JPanel nameDictionaryPanel;
    private JLabel welcome1; 
    private JLabel welcome2;
    private JLabel namePrompt;
    private JLabel enterPrompt;
    private JLabel imageAsLabel;
    private ImageIcon dictImage;
    private Dictionary dict;

    // MODIFIES: this
    // EFFECTS: initializes the load screen UI panel and sets up the initial layout and components
    public LoadScreen(WelcomeUI controller) {
        this.controller = controller;
        
        jsonReader = new JsonReader(STORE_FILE);
        setLayout(new GridLayout(2, 1));

        imagePanel = new JPanel();
        promptPanel = new JPanel();
        promptPanel.setLayout(new GridLayout(3, 1));
        nameDictionaryPanel = new JPanel();
        nameDictionaryPanel.setLayout(new GridLayout(3, 1));

        imagePanel.setBackground(new Color(162, 199, 219));
        promptPanel.setBackground(new Color(162, 199, 219));
        nameDictionaryPanel.setBackground(new Color(162, 199, 219));

        placeImage();
        placeWelcome();
        placeLoadButtons();

        add(imagePanel);
        add(promptPanel);
    }

    

    // MODIFIES: this
    // EFFECTS: places a dictionary image into the UI
    private void placeImage() {
        dictImage = new ImageIcon("./data/images/dictionary.jpg");
        imageAsLabel = new JLabel(dictImage);
        imagePanel.add(imageAsLabel);
    }

    // MODIFIES: this
    // EFFECTS: adds welcome messages to the prompt panel
    private void placeWelcome() {
        welcome1 = new JLabel(WELCOME_MSG_1, JLabel.CENTER);
        welcome1.setSize(WIDTH, HEIGHT / 4);
        welcome1.setFont(new Font("Arial", Font.BOLD, 16));
        promptPanel.add(welcome1);
        
        welcome2 = new JLabel(WELCOME_MSG_2,  JLabel.CENTER);
        welcome2.setSize(WIDTH, HEIGHT / 4);
        welcome2.setFont(new Font("Arial", Font.PLAIN, 16));
        promptPanel.add(welcome2);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to load a new or existing dictionary and attaches their action listeners
    private void placeLoadButtons() {
        JButton b1 = new JButton("New");
        JButton b2 = new JButton("Load");
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(162, 199, 219));

        buttons.add(b1);
        buttons.add(b2);
        buttons.setSize(WIDTH, HEIGHT / 6);
        promptPanel.add(buttons);

        b1.addActionListener(e -> {
            resetUI();
            nameNewDictionary();
        });

        b2.addActionListener(e -> {
            try {
                dict = jsonReader.read();
                openDictionary();
            } catch (IOException ioe) {
                welcome2.setText("Sorry, there is no dictionary in your save file. Please create a new one");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: resets the UI by removing all components and repainting the panel
    private void resetUI() {
        removeAll();
        repaint();
        revalidate();
    }
    
    // MODIFIES: this, dict
    // EFFECTS: sets up the UI for entering a new dictionary name
    private void nameNewDictionary() {
        placeImage();
        placePrompts();
        
        JTextField enterName = new JTextField();
        enterName.setFont(new Font("Arial", Font.PLAIN, 16));
        nameDictionaryPanel.add(enterName);

        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dict = new Dictionary(enterName.getText());
                openDictionary();
            }
        });

        add(imagePanel);
        add(nameDictionaryPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds labels for prompts to name the new dictionary
    private void placePrompts() {
        namePrompt = new JLabel(ENTER_NAME_MSG1, JLabel.CENTER);
        namePrompt.setSize(WIDTH, HEIGHT / 3);
        namePrompt.setFont(new Font("Arial", Font.BOLD, 16));
        enterPrompt = new JLabel(ENTER_NAME_MSG2, JLabel.CENTER);
        enterPrompt.setSize(WIDTH, HEIGHT / 3);
        enterPrompt.setFont(new Font("Arial", Font.ITALIC, 16));
        
        nameDictionaryPanel.add(namePrompt);
        nameDictionaryPanel.add(enterPrompt);
    }

    // MODIFIES: this
    // EFFECTS: opens the main DictionaryUI and disposes the WelcomeUI frame
    private void openDictionary() {
        controller.dispose();
        new DictionaryUI(dict);
    }
}