package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a dictionary with a name and list of terms
public class Dictionary implements Writable {
    String name;
    ArrayList<Term> terms;

    public Dictionary(String name) {
        this.name = name;
        this.terms = new ArrayList<Term>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Term> getTerms() {
        EventLog.getInstance().logEvent(new Event("Viewing all terms"));
        return terms; 
    }

    // REQUIRES: inputted term is not already in terms
    // MODIFIES: this
    // EFFECTS: adds the given term to the dictionary object's terms list and logs the event
    public void addTerm(Term term) {
        terms.add(term);
        EventLog.getInstance().logEvent(new Event("Added term: " + term.getName()));
    }

    // REQUIRES: terms.size() > 0
    // MODIFIES: this
    // EFFECTS: deletes the term with the name termName from the dictionary object's terms list and logs the event
    //          if no term exists with the name, do nothing
    public void deleteTerm(String termName) {
        for (int i = 0; i < terms.size(); i++) {
            if (termName.equalsIgnoreCase(terms.get(i).getName())) {
                terms.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed term: " + termName));
                i--;    // removing from list changes its size, this is a rescaling procedure of i to ensure no term is 
                        //skipped
            }
        }
    }
    
    // EFFECTS: if the term with the name termName isn't in the list, returns null
    //          otherwise, returns the term with the name termName and logs the event           
    public Term findTerm(String termName) {
        for (Term t : terms) {
            if (termName.equalsIgnoreCase(t.getName())) {
                EventLog.getInstance().logEvent(new Event("Successfully found term: " + termName));
                return t;
            }
        }
        return null;
    }

    // EFFECTS: filters through terms, returning a list of only favourites, and logs the event
    public ArrayList<Term> filterFavTerms() {
        ArrayList<Term> favs = new ArrayList<>();
        
        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).getFavourite()) {
                favs.add(terms.get(i));
            }  
        }
        
        EventLog.getInstance().logEvent(new Event("Viewing favourite terms"));
        return favs; 
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        JSONArray jsonTerms = termsToJson();
        json.put("terms", jsonTerms);
        return json;
    }

    // EFFECTS: returns terms in this dictionary as a JSON array
    private JSONArray termsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Term t : terms) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
