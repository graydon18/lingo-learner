package persistence;

import model.Dictionary;
import model.Event;
import model.EventLog;

import org.json.JSONObject;

import java.io.*;

// A class representing a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String file;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    //          throws FileNotFoundException if destination file cannot be opened for writing
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(file));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of dictionary to file and logs the event
    public void write(Dictionary dict) {
        JSONObject json = dict.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved " + dict.getName()));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

// I used the Workroom App as a reference as it informed me on JSON writing processes and code
