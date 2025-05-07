package ui.console;

import model.Dictionary;
import model.Term;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// A dictionary application that allows the user to add, view, edit, and delete their terms
public class ConsoleDictionaryApp {
    private static final String STORE_FILE = "./data/myDictionary.json";
    private Dictionary dict;
    private Scanner scanner;
    private boolean isProgramRunning;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates an instance of the DictionaryApp console ui app
    public ConsoleDictionaryApp() {
        dictAppConstructor();

        System.out.println("\nWelcome to your language learning assistant, LingoLearner.");
        System.out.println("\nEnter one of the following options");

        System.out.println("\n1 - Create a new dictionary (NOTE: this will delete the previously saved dictionary)");
        System.out.println("2 - Load your dictionary\n");
        
        handleMenuInput();

        while (isProgramRunning) {
            dictMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with starting values
    public void dictAppConstructor() {
        dict = new Dictionary("placeholder");
        scanner = new Scanner(System.in);
        isProgramRunning = true;
        jsonWriter = new JsonWriter(STORE_FILE);
        jsonReader = new JsonReader(STORE_FILE);
    }

    // EFFECTS: processes the user's input and runs the according initial command
    public void handleMenuInput() {
        String input = scanner.nextLine();

        while (!(input.equals("1") || input.equals("2"))) {
            System.out.println("\nSorry, that input is invalid. Please try again");
            input = scanner.nextLine(); 
        }

        if (input.equals("1")) {
            nameDictionary();
        } else {
            loadDictionary();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the dictionary's name to the inputted value from the user
    public void nameDictionary() {
        System.out.println("\nWhat would you like to name your personal dictionary?\n");
        String input = scanner.nextLine();
        
        dict.setName(input);

        System.out.println("\n" + dict.getName() + ", huh. I love that name!");
    }

    // MODIFIES: this
    // EFFECTS: loads dictionary from file
    //          if exception is caught, leads user to create a new dictionary
    private void loadDictionary() {
        try {
            dict = jsonReader.read();
            System.out.println("\nLoaded " + dict.getName());
        } catch (IOException e) {
            System.out.println("\nSorry, there is no dictionary in your save file. Please create a new one");
            nameDictionary();
        }
    }

    // EFFECTS: displays menu options and processes user input for menu commands
    public void dictMenu() {
        displayOptions();
        String input = scanner.nextLine();
        menuChoice(input);
    }

    // EFFECTS: displays all dictionary functions the user can use with associated entry keys
    public void displayOptions() {
        System.out.println("\nEnter one of the following options\n");

        System.out.println("1 - Add a new term to your dictionary");
        System.out.println("2 - Remove a term from your dictionary");
        System.out.println("3 - Search for a term in your dictionary (you can view, edit, or (un)favourite a " 
                            + "term from here)");
        System.out.println("4 - View all of the terms in your dictionary");
        System.out.println("5 - View all of your favourite terms in your dictionary");
        System.out.println("6 - Save your dictionary to file");
        System.out.println("7 - Quit the application\n");
    }

    // EFFECTS: processes the user's input and runs the according command, returning to menu if input isn't defined
    public void menuChoice(String input) {
        if (input.equals("1")) {
            addNewTerm();
        } else if (input.equals("2")) {
            removeTerm();
        } else if (input.equals("3")) {
            searchTerm();
        } else if (input.equals("4")) {
            viewAllTerms();
        } else if (input.equals("5")) {
            viewFavTerms();
        } else if (input.equals("6")) {
            saveDictionary();
        } else if (input.equals("7")) {
            quitApp();
        } else {
            System.out.println("\nSorry, that input is invalid. Please try again\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new specified term to their dictionary
    public void addNewTerm() {
        System.out.println("\nWhat is the new term you'd like to add? If you would like to return to menu, type 0");
        String name = scanner.nextLine();
        if (name.equals("0")) {
            return;
        }

        System.out.println("\nWhat kind of term is it? (noun, verb, phrase, etc.)");
        String gtype = scanner.nextLine();

        System.out.println("\nWhat is its translation?");
        String trnsl = scanner.nextLine();

        System.out.println("\nPlease provide an example sentence with this term");
        String ex = scanner.nextLine();

        Term newTerm = new Term(name, gtype, trnsl, ex);
        newTerm.setNotes(addNotes());
        checkAddAsFavourite(newTerm); 

        dict.addTerm(newTerm);
        System.out.println("\nYou new term has been created and added to your dictionary!");
    }

    // EFFECTS: creates an option for users to add notes to the term being added to their dictionary
    public String addNotes() {
        System.out.println("\nWould you like to add any notes about your new term? Enter 1 for yes, otherwise enter" 
                            + "any other key");
        String input = scanner.nextLine(); 

        if (input.equals("1")) {
            System.out.println("\nWhat notes would you like to add?");
            String notes = scanner.nextLine();
            return notes;
        } else {
            return "";
        }
    }

    // MODIFIES: newTerm
    // EFFECTS: creates an option for users to set the term being added to their dictionary as a favourite term
    public void checkAddAsFavourite(Term newTerm) {
        System.out.println("\nWould you like to mark this word as a favourite? Enter 1 for yes, otherwise enter any" 
                            + "other key");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            newTerm.setFavourite();
            System.out.println("\nThis term has been favourited!");
        }
    }

    // MODIFIES: this
    // EFFECTS: if the term entered by the user doesn't exist in their dictionary, the function does nothing
    //          otherwise, removes the term from their dictionary
    public void removeTerm() {
        if (dict.getTerms().isEmpty()) {
            System.out.println("\nSorry, your dictionary has no terms. Try adding a term first!");
            return;
        }
        
        System.out.println("\nEnter the name of the term you'd like to delete from your dictionary. "
                            + "If you would like to return to menu, type 0");
        String termName = scanner.nextLine();
        if (termName.equals("0")) {
            return;
        }

        dict.deleteTerm(termName);
    }

    // EFFECTS: finds a specified term in their dictionary and prompts 3 commands involving the term
    public void searchTerm() {
        if (dict.getTerms().isEmpty()) {
            System.out.println("\nSorry, your dictionary has no terms. Try adding a term first!");
            return;
        }
        
        System.out.println("\nEnter the name of the term you'd like to find in your dictionary. "
                            + "If you would like to return to menu, type 0");
        String termName = scanner.nextLine();
        if (termName.equals("0")) {
            return;
        }
        Term term = dict.findTerm(termName);
        
        while (term == null) {
            System.out.println("\nSorry, there is no term in your dictionary with that name. "
                                + "Please try again or enter 0 to return to the menu");
            termName = scanner.nextLine();
            if (termName.equals("0")) {
                return;
            }
            term = dict.findTerm(termName);
        }

        System.out.println("\nYour term has been found!");
        handleSearchTerm(term);
    }
    
    // EFFECTS: displays search term options and processes user input for search term commands
    public void handleSearchTerm(Term term) {
        displaySearchOptions();
        handleSearchOptionsInput(term);
    }

    // EFFECTS: displays all search term functions the user can use with associated entry keys
    public void displaySearchOptions() {
        System.out.println("\nWhat would you like to do with the term? Enter one of the following options\n");
        
        System.out.println("1 - View the term and its details");
        System.out.println("2 - Edit the term");
        System.out.println("3 - Favourite or unfavourite the term\n");
    }

    // EFFECTS: processes the user's input and runs the according search term command
    public void handleSearchOptionsInput(Term term) {
        String input = scanner.nextLine();

        while (!(input.equals("1") || input.equals("2") || input.equals("3"))) {
            System.out.println("\nSorry, that input is invalid. Please try again");
            input = scanner.nextLine(); 
        }

        if (input.equals("1")) {
            viewFullTerm(term);
        } else if (input.equals("2")) {
            editTerm(term);
        } else {
            handleFavourite(term);   
        }
    }

    // EFFECTS: prints out the given term and all of its details
    public void viewFullTerm(Term term) {
        System.out.println("\nHere is your entry for " + term.getName() + " (" + term.getGType() + ")\n");
        System.out.println("Translation: " + term.getTranslation());
        System.out.println("Example Sentence: " + term.getExample());
        
        if (term.getNotes().equals("")) {
            System.out.println("Notes: N/A");
        } else {
            System.out.println("Notes: " + term.getNotes());
        }
       
        if (term.getFavourite()) {
            System.out.println("\n* FAVOURITE WORD *\n");
        }
    }
    
    // EFFECTS: displays edit term options and processes user input for edit term commands
    public void editTerm(Term term) {
        displayEditOptions();
        String input = scanner.nextLine();
        handleEditOptions(term, input);
    }

    //EFFECTS: displays all edit term functions the user can use with associated entry keys
    private void displayEditOptions() {
        System.out.println("\nWhat would you like to change about the term? Enter one of the following options\n");
        
        System.out.println("1 - Name");
        System.out.println("2 - Term Type");
        System.out.println("3 - Translation"); 
        System.out.println("4 - Example Sentence");
        System.out.println("5 - Notes\n");
    }

    // MODIFIES: term
    // EFFECTS: processes the user's input and runs the according edit term command
    private void handleEditOptions(Term term, String input) {
        while (!(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") 
                || input.equals("5"))) {
            System.out.println("\nSorry, that input is invalid. Please try again");
            input = scanner.nextLine(); 
        }
        
        if (input.equals("1")) {
            System.out.println("\nWhat would you like to rename your term to?");
            term.setName(scanner.nextLine()); 
        } else if (input.equals("2")) {
            System.out.println("\nWhat would you like to change the term type to?");
            term.setGType(scanner.nextLine());
        } else if (input.equals("3")) {
            System.out.println("\nWhat would you like to change the translation to?");
            term.setTranslation(scanner.nextLine());  
        } else if (input.equals("4")) {
            System.out.println("\nWhat would you like to change the example sentence to?");
            term.setExample(scanner.nextLine());  
        } else {
            System.out.println("\nWhat would you like to change the notes to?");
            term.setNotes(scanner.nextLine());  
        }
    }

    // MODIFIES: term
    // EFFECTS: if the term is already favourite, it sets to unfavourite, and vice-versa
    public void handleFavourite(Term term) {
        if (term.getFavourite()) {
            term.setUnfavourite();
            System.out.println("\nYour term is no longer a favourite term");
        } else {
            term.setFavourite();
            System.out.println("\nYour term is now a favourite term!");
        }
    }

    // EFFECTS: displays all terms in the dictionary with their translations
    public void viewAllTerms() {
        if (dict.getTerms().isEmpty()) {
            System.out.println("\nSorry, your dictionary has no terms. Try adding a term first!");
            return;
        }
        
        int countTerms = 1;
        for (int i = 0; i < dict.getTerms().size(); i++) {
            System.out.println(countTerms + ". " + dict.getTerms().get(i).getName() + " - " 
                                + dict.getTerms().get(i).getTranslation());
            countTerms++;
        }
    }
    
    // EFFECTS: displays all terms in the dictionary marked as favourite with their translations
    public void viewFavTerms() {
        if (dict.getTerms().isEmpty()) {
            System.out.println("\nSorry, your dictionary has no terms. Try adding a term first!");
            return;
        }
        
        int countTerms = 1;
        for (int i = 0; i < dict.getTerms().size(); i++) {
            if (dict.getTerms().get(i).getFavourite()) {
                System.out.println(countTerms + ". " + dict.getTerms().get(i).getName() + " - " 
                                    + dict.getTerms().get(i).getTranslation());
                countTerms++;
            }
        }
    }

    // EFFECTS: saves the dictionary to file
    private void saveDictionary() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(dict);
            jsonWriter.closeWriter();
            System.out.println("\nSaved " + dict.getName());
        } catch (FileNotFoundException e) {
            System.out.println("\nUnable to write to file: " + STORE_FILE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a closing message, and marks the program as ended
    public void quitApp() {
        System.out.println("\nThanks for using LingoLearner! Have a great day!");
        this.isProgramRunning = false;
    }
}

// I used the FlashcardReviewer from Lab 4 as a reference as it informed me on terminal ui structure