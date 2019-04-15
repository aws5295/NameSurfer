/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "eRic"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import acm.util.ErrorException;

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		// Initialize Database
		database = new HashMap<String, NameSurferEntry>();
		
		// Open the Data File and parse each line into a "Name" and a "NameSurferEntry"
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				String name = firstWord(line.toUpperCase());
				database.put(name, new NameSurferEntry(line));
				names.add(name);
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
/* Method: firstWord(line) */
/**
 * This method returns a name out of a structured line.
 * Example line: 'Adam 0 0 0 5 5 6 7 200 150 190'
 * This function would return "Adam" from the above line.
 */
	private String firstWord(String line) {
		int i = line.indexOf(' ');
		return line.substring(0, i);
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		// Return null if name doesn't exist
		if (database.containsKey(name)) {
			return database.get(name);
		} else return null;
	}
	
/* Method: getNames() */
/**
 * Returns an ArrayList of every name in the database
 */
	public ArrayList<String> getNames(){
		return names;
	}
	
/* Instance Variables */
	private HashMap<String, NameSurferEntry> database;
	private ArrayList<String> names = new ArrayList<String>();
}

