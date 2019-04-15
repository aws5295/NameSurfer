/*
 * File: NameSurfer.java
 * ---------------------
 * NameSurfer allows the user to enter a name in the text box. 
 * The user also sets how many correlated names they want to graph along with the original entry.
 * Hitting Graph will display the original and the N most correlated names.
 * Hitting Clear will clear the display.
 */

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import acm.program.Program;

public class NameSurfer extends Program implements NameSurferConstants {

	private static final long serialVersionUID = -8594390144338365533L;

	public static void main(String[] args) {
		new NameSurfer().start(args);
	}
	
/* Method: init() */
/**
 * Calls the setup functions to start the program.
 * Initializes the database.
 */
	public void init() {
	    // Add components and set them up to listen
		createController();
		addActionListeners();
		
		// Initialize the database and name list
		db = new NameSurferDataBase(NAMES_DATA_FILE);
		names = db.getNames();
		
		// Add the NameSurferGraph
		graph = new NameSurferGraph();
		add(graph);
	}

/* Method: createController() */
/**
 * Creates the components for the program, including text entry,
 * "Graph" button, "Clear" button, and the slider.  Adds the components to the screen.
 */
	private void createController() {
		// Add the Textfield and start the listener
		nameField = new JTextField(25);
		nameField.addActionListener(this);
		
		// Create buttons
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		
		// Create Slider
		slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEFAULT);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		// slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
		
		// Add Interactors to the SOUTH region
		add(new JLabel("Name:"), SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
		add(new JLabel("   Correlated Names:"), SOUTH);
		add(slider, SOUTH);
		
		// Create Combo Box and add it
		cBox = new JComboBox();
		cBox.addItem("Correlation");
		cBox.addItem("Mean Square Error");
		cBox.setEditable(false);
		cBox.setSelectedIndex(0);
		add(new JLabel("    Analysis Type:"), SOUTH);
		add(cBox, SOUTH);
	}

/* Method: actionPerformed(event) */
/**
 * This method is called whenever an event happens on the screen.
 * Valid events are: 
 * 		- User Clicks a button ("Graph" or "Clear")
 * 		- User hits Enter when typing in the text entry
 * 
 * If the user hit "Graph" or enter, the value of the slider is read, a list
 * of correlated names is generated, and the graphing functions are called.
 * 
 * If "Clear" is detected, then all objects are removed from teh graph. 
 */
	public void actionPerformed(ActionEvent e) {
		// Local variables
		Object source = e.getSource();
		String entry = nameField.getText().toUpperCase();
		int matches = slider.getValue();
	
		// If there is a name to graph
		if (source == graphButton || source == nameField) {
			// Add the original name
			graph.addEntry(db.findEntry(entry));
			graph.update();
			
			
			if (matches > 0){
				// Add each MSE name to the graph
				if (cBox.getSelectedItem().equals("Mean Square Error")) {
					closestNames = nsMath.getMSE(matches, entry, names, db);
				}
				else {
					// Add each correlated name to the graph
					closestNames = nsMath.getCorr(matches, entry, names, db);
				}
				
				for (int i = 0; i < matches; i++) {
					graph.addEntry(db.findEntry(closestNames[i][0].toUpperCase()));
					graph.update();
				}
			}
		
		// Clear the screen
		} else if (source == clearButton) {
			graph.clear();
			graph.update();
		}
	
		// Set the entry blank
		nameField.setText("");
	}

/* Instance variables */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private JSlider slider;
	private JComboBox cBox;
	private NameSurferDataBase db;
	private NameSurferGraph graph;
	private ArrayList<String> names = new ArrayList<String>();
	private NameSurferMath nsMath = new NameSurferMath();
	private String[][] closestNames;
}
