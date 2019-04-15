/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.StringTokenizer;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		// Initialize Ranks array to be all zeros
		ranks = new Integer[NDECADES];
		for (int i = 0; i < ranks.length; i++) {
			ranks[i] = 0;
		}
		
		// Create tokenizer to iterate through ranks
		StringTokenizer tokenizer = new StringTokenizer(line);
		
		// First token is the name
		String token = tokenizer.nextToken();
		if (token != null) name = token;
		
		// Next 11 tokens are the data
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			if (token != null) {
				ranks[i] = Integer.parseInt(token);
			}
			i++;
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		String result = name;
		return result;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		if ((decade < 0) || (decade >= NDECADES)) {
			return ranks[0];
		} else return ranks[decade];
	}

/* Method: toArray() */
/**
 * Helper function to return an array of ranks without the name.
 */
	public Integer[] toArray() {
		return ranks;
	}
	
/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String result = name + "[";
		for (int i = 0; i < ranks.length; i++) {
			result = result + " " + ranks[i];

		}
		result += " ]";
		return result;
	}
	
/* Instance Variables */
	private String name = "";
	private Integer[] ranks;
}