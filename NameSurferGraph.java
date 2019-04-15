/*
 * File: NameSurferGraph.java
 * --------------------------
 * This Class is responsible for graphic NameSurferEntries.
 * This Class also resizes.  It maintains a list of every object that should be on
 * the display.  Whenever the display is resized the objects are cleared and reprinted
 * based on the new dimenssions of the screen
 */

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Iterator;
import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	private static final long serialVersionUID = 5395583038697050659L;

/* Constructor: NameSurferGraph() */
/**
* Creates a new NameSurferGraph object that displays the data.
*/
	public NameSurferGraph() {
		addComponentListener(this);
		names = new HashMap<String, NameSurferEntry>();
		colors = new HashMap<String, Color>();
		colorPicker = new ColorPicker();
	}
	
/* Method: clear() */	
/**
* Clears the list of name surfer entries stored inside this class.
*/
	public void clear() {
		// Remove all Entries from the list of NameSurferEntries
		names.clear();
		colorPicker.reset();
	}
	
/* Method: addEntry(entry) */
/**
* Adds a new NameSurferEntry to the list of entries on the display.
* Note that this method does not actually draw the graph, but
* simply stores the entry; the graph is drawn by calling update.
*/
	public void addEntry(NameSurferEntry entry) {
		if (entry != null) {
			names.put(entry.getName(), entry);
			colors.put(entry.getName(), colorPicker.nextColor());
		}
	}

/* Method: update() */
/**
* Updates the display image by deleting all the graphical objects
* from the canvas and then reassembling the display according to
* the list of entries. Your application must call update after
* calling either clear or addEntry; update is also called whenever
* the size of the canvas changes.
*/
	public void update() {
		removeAll();
		drawGridLines();
		drawNameSurferEntries();
	}

/* Method: drawGridLines() */
/**
 * This method adds the grid lines to the screen.
 * Also responsible for adding the X-axis Labels.
 */
	private void drawGridLines() {
		// Draw Margins
		GLine top = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine bottom = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(top);
		add(bottom);
		
		// Draw vertical years
		double xPos = 0;
		double spacing = getWidth() / (double)NDECADES;
		Integer year = 0;
		for (int i = 0; i<NDECADES; i++) {
			// Add the vertical Lines
			GLine line = new GLine(xPos, 0, xPos, getHeight());
			add(line);
			
			// Add the Year Labels
			year = START_DECADE + i*10;
			GLabel topLabel = new GLabel(year.toString(), xPos + 5, getHeight() - 5);
			GLabel botLabel = new GLabel(year.toString(), xPos + 5, GRAPH_MARGIN_SIZE -5);
			add(topLabel);
			add(botLabel);
			
			xPos += spacing;
		}
	}

/* Method: drawNameSurferEntries() */
/**
 * This helper function calls the draw method for every entry
 * that should be on the display.
 * Called when the display resizes.
 */
	private void drawNameSurferEntries() {
		
		// Call drawNameSurferEntry for each iterator in the names HashMap
		Iterator<String> it = names.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			drawNameSurferEntry(names.get(name), colors.get(name));
		}
		
	}

/* Method drawNameSurferEntry(entry, color) */
/** 
 * This method draws lines connecting the data points on the graph.
 * The graph is drawn using the color passed to the function.
 * A label with the name and rank of each point is also added.
 */
	private void drawNameSurferEntry(NameSurferEntry entry, Color color) {
		double spacing = getWidth() / (double) NDECADES;
		double x0 = 0;
		double x1 = spacing;
		double rank0 = 0;
		double rank1 = 0;
		String strLabel = "";
		
		for (int i=0; i<NDECADES -1; i++) {
			rank0 = entry.getRank(i);
			rank1 = entry.getRank(i+1);
			
			// Build the Line
			GLine line = new GLine(x0, rankToYCord(rank0), x1, rankToYCord(rank1));
			line.setColor(color);
			add(line);
			
			// Build the Label
			strLabel = entry.getName() + ": " + entry.getRank(i);
			GLabel label = new GLabel(strLabel, x0, rankToYCord(rank0));
			label.setColor(color);
			add(label);
			
			x0 += spacing;
			x1 += spacing;
		}
		
		// Add the final label
		strLabel = entry.getName() + ": " + entry.getRank(NDECADES -1);
		GLabel label = new GLabel(strLabel, x0, rankToYCord(rank1));
		label.setColor(color);
		add(label);
		
	}

/* Method: rankToYCord(rank) */
/**
 * This helper converts a rank to a Y-Coordinate.
 * Ranks:
 * 		1 is highest
 * 		1000 is lowest
 * 		0 represents a rank less than 1000
 */
	private double rankToYCord(double rank) {
		double result = 0;
		
		if ((rank <= 0) || (rank >= MAX_RANK)) {
			result = getHeight() - GRAPH_MARGIN_SIZE;
		} else {
			result = GRAPH_MARGIN_SIZE + (double)(getHeight() - GRAPH_MARGIN_SIZE*2)*((rank-1.0)/(double)MAX_RANK);
		}
		
		return result;
	}
	
/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
/* Instance Variables */
	private HashMap<String, NameSurferEntry> names;
	private HashMap<String, Color> colors;
	private ColorPicker colorPicker;
}