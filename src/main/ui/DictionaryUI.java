package ui;

import model.Dictionary;
import model.Event;
import model.EventLog;
import ui.tabs.Tab;
import ui.tabs.AddTab;
import ui.tabs.HomeTab;
import ui.tabs.SearchTab;

import javax.swing.*;
import java.awt.*;

// A Dictionary UI that allows the users to add, favourite, view and remove terms from their dictionary
public class DictionaryUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int ADD_TAB_INDEX = 1;
    public static final int SEARCH_TAB_INDEX = 2;
    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    
    private JTabbedPane tabbar;
    private Tab homeTab;
    private Tab addTab;
    private Tab searchTab;

    //MODIFIES: this
    //EFFECTS: creates DictionaryUI, displays sidebar and tabs
    public DictionaryUI(Dictionary dict) {
        super(dict.getName());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(162, 199, 219));

        tabbar = new JTabbedPane();
        tabbar.setTabPlacement(JTabbedPane.TOP);

        addPrintEventLogAction();
        loadTabs(dict);
        add(tabbar);

        setVisible(true);
    }

    //EFFECTS: adds window closing event, prints events from event log when window is closed
    private void addPrintEventLogAction() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Event Log: \n");
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString() + "\n");
                }
                dispose();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs(Dictionary dict) {
        homeTab = new HomeTab(this, dict);
        addTab = new AddTab(this, dict);
        searchTab = new SearchTab(this, dict);

        tabbar.add(homeTab, HOME_TAB_INDEX);
        tabbar.setTitleAt(HOME_TAB_INDEX, "Your Dictionary");
        tabbar.add(addTab, ADD_TAB_INDEX);
        tabbar.setTitleAt(ADD_TAB_INDEX, "Add");
        tabbar.add(searchTab, SEARCH_TAB_INDEX);
        tabbar.setTitleAt(SEARCH_TAB_INDEX, "Search");
    }
}
