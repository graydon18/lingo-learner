package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// A class representing a log of dictionary events. We use the Singleton Design Pattern to ensure that there is only one
// instance in the whole program
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;
	
    // MODIFIES: this
	// EFFECTS: creates a new event log
    private EventLog() {
    	events = new ArrayList<Event>();
    }
	
    // MODIFIES: this
	// EFFECTS: gets instance of EventLog; creates it if it doesn't already exist
    public static EventLog getInstance() {
    	if (theLog == null) {
    	    theLog = new EventLog();
        }
		
    	return theLog;
    }
	
	
    // MODIFIES: this
    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
    	events.add(e);
    }
	
	// MODIFIES: this
	// EFFECTS: clears the event log and logs the event
    public void clear() {
    	events.clear();
    	logEvent(new Event("Event log cleared."));
    }
	
    @Override
    public Iterator<Event> iterator() {
    	return events.iterator();
    }
}
