package persistence;

import model.Dictionary;
import model.Event;
import model.EventLog;
import model.Term;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// A class representing a reader that reads dictionary from JSON data stored in file
public class JsonReader {
    private String file;

    // EFFECTS: constructs reader to read from file
    public JsonReader(String file) {
        this.file = file;
    }

    // EFFECTS: reads the dictionary from file and returns it, logging start of load event
    //          throws IOException if an error occurs reading data from file
    public Dictionary read() throws IOException {
        String data = readFile(file);
        JSONObject jsonObject = new JSONObject(data);
        EventLog.getInstance().logEvent(new Event("Loading dictionary..."));
        return parseDictionary(jsonObject);
    }

    // EFFECTS: reads file as string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the dictionary from JSON object and returns it and logs load event
    private Dictionary parseDictionary(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Dictionary dict = new Dictionary(name);
        parseTerms(dict, jsonObject);
        EventLog.getInstance().logEvent(new Event("Successfully loaded dictionary: " + dict.getName()));
        return dict;
    }

    // MODIFIES: dict
    // EFFECTS: parses terms from JSON object and adds them to dictionary
    private void parseTerms(Dictionary dict, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("terms");
        for (Object json : jsonArray) {
            JSONObject term = (JSONObject) json;
            parseTerm(dict, term);
        }
    }

    // MODIFIES: dict
    // EFFECTS: parses term from JSON object and adds it to dictionary
    private void parseTerm(Dictionary dict, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String gtype = jsonObject.getString("grammarType");
        String transl = jsonObject.getString("translation");
        String ex = jsonObject.getString("example");
        String notes = jsonObject.getString("notes");
        boolean fav = jsonObject.getBoolean("isFavourite");
        Term term = new Term(name, gtype, transl, ex);
        term.setNotes(notes);
        dict.addTerm(term);
        if (fav) {
            term.setFavourite();
        } 
    }
}

// I used the Workroom App as a reference as it informed me on JSON reading processes and code
