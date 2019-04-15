/*
 * File: ColorPIcker.java
 * ---------------------
 * This class generates Java.awt.Color objects.
 * Every time nextColor() is called, a different color is returned.
 */

import java.awt.Color;

public class ColorPicker {
	
/* Constructor: ColorPicker() */
/**
 * Creates a new ColorPicker object.
 */
	public ColorPicker() {
		counter = 0;
	}

/* Method: nextColor() */
/**
 * This method returns a different Color every time it is called.
 * Once the max number of Colors is exhausted, the Colors repeat.
 */
	public Color nextColor() {
		Color result = null;
		
		if (counter == 0) {
			result = Color.BLACK;
		} else if (counter == 1) {
			result = Color.red;
		} else if (counter == 2) {
			result = Color.GREEN;
		} else if (counter == 3) {
			result = Color.BLUE;
		} else if (counter == 4) {
			result = Color.ORANGE;
		} else if (counter == 5) {
			result = Color.CYAN;
		}
		
		if (counter == 5) {
			counter = 0;	
		} else {
			counter++;
		}
		return result;
	}
	
/* Method: reset() */
/**
 * This starts the counter back at 0.
 */
	public void reset() {
		counter = 0;
	}
	
/* Instance variables */
	private int counter;
}