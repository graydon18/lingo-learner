package ui;

import javax.swing.*;

// GitHub author test comment

// A program welcome UI that allows the users to load or create a new dictionary
public class WelcomeUI extends JFrame {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;

    private LoadScreen loadScreen;
    
    //EFFECTS: creates a new instance of a WelcomeUI
    public static void main(String[] args) {
        new WelcomeUI();
    }

    //MODIFIES: this
    //EFFECTS: creates WelcomeUI and creates its load panel
    private WelcomeUI() {
        super("LingoLearner");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createLoadScreen();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds the load screen to this UI
    private void createLoadScreen() {
        loadScreen = new LoadScreen(this);

        add(loadScreen);
    }
}    