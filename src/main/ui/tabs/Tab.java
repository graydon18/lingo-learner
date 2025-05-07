package ui.tabs;

import javax.swing.JPanel;

import model.Dictionary;
import ui.DictionaryUI;

// A panel that is controlled by a given DictionaryUI
public class Tab extends JPanel {
    private final DictionaryUI controller;
    protected Dictionary dict;

    // REQUIRES: DictionaryUI controller that holds this tab
    // MODIFIES: this
    // EFFECTS: creates a new tab instance with a DictionaryUI controller
    public Tab(DictionaryUI controller, Dictionary dict) {
        this.controller = controller;
        this.dict = dict;
    }

    // EFFECTS: returns the DictionaryUI controller for this tab
    public DictionaryUI getController() {
        return controller;
    }
}

// I used the SmartHome program as a guide for this Tab class relationship
