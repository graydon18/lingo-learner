package model;

import org.json.JSONObject;

import persistence.Writable;

// A class representing a term with a name, category, translation, example sentence, notes, and favourite marker
public class Term implements Writable {
    String name;
    String grammarType;
    String translation;
    String example;
    String notes;
    boolean isFavourite;

    public Term(String name, String gtype, String translation, String ex) {
        this.name = name;
        this.grammarType = gtype;
        this.translation = translation;
        this.example = ex;
        this.notes = "";
        this.isFavourite = false;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public void setGType(String gtype) {
        this.grammarType = gtype;
    }

    public void setTranslation(String trnsl) {
        this.translation = trnsl;
    }

    public void setExample(String ex) {
        this.example = ex;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // EFFECTS: sets term's isFavourtie as true and logs the event
    public void setFavourite() { // setter
        this.isFavourite = true;
        EventLog.getInstance().logEvent(new Event("Favourited term: " + getName()));
    }

    // EFFECTS: sets term's isFavourtie as false and logs the event
    public void setUnfavourite() { 
        this.isFavourite = false;
        EventLog.getInstance().logEvent(new Event("Unfavourited term: " + getName()));
    }

    public String getName() { 
        return name; 
    }

    public String getGType() {
        return grammarType;
    }

    public String getTranslation() {
        return translation;
    }

    public String getExample() {
        return example;
    }

    public String getNotes() {
        return notes;
    }

    public boolean getFavourite() {
        return isFavourite;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("grammarType", grammarType);
        json.put("translation", translation);
        json.put("example", example);
        json.put("notes", notes);
        json.put("isFavourite", isFavourite);
        return json;
    }
}
