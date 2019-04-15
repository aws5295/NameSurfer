/*
 * File: NameSurferMath.java
 * --------------------------
 * This class does performs some various functions with name surfer entries.
 */

import java.util.ArrayList;

public class NameSurferMath {

/* Constructor: NameSurferMath() */
/**
 * Creates a new NameSurferMath Object.
 */
	public NameSurferMath(){
		
	}
	
/* Method: calcCorrelation(a, b) */
/**
 * This function returns the Pearson Correlation between to arrays
 * of equal length.
 */
	public double calcCorrelation(Integer[] a, Integer[] b){
		double len = (double) a.length;
		if ((int) len != b.length) return 10;

		int sumAB = 0;
		int sumA = 0;
		int sumB = 0;
		int sumAsq = 0;
		int sumBsq = 0;
		
		for (int i = 0; i < len; i++) {
			sumAB += a[i] * b[i];
			sumA += a[i];
			sumB += b[i];
			sumAsq += a[i] * a[i];
			sumBsq += b[i] * b[i];
		}
		
		// Find covariation, standard error of x, standared error of y
		double cov = (sumAB / len) - (sumA*sumB / len / len);
		double sigmaA = Math.sqrt(sumAsq/len - sumA*sumA/len/len);
		double sigmaB = Math.sqrt(sumBsq/len - sumB*sumB/len/len);
		
		return cov/sigmaA/sigmaB;
	}
	
/* Method: calcMSE(a, b) */
/**
 * This function returns the Mean Square Error between to arrays
 * of equal length.
 */
	public double calcMSE(Integer[] a, Integer[] b){
		double len = (double) a.length;
		if ((int) len != b.length) return 10000;
		
		double mse = 0.0;
		
		for (int i = 0; i < len; i++) {
			mse += Math.pow(a[i] - b[i], 2);
		}
			
		return Math.sqrt(mse);
	}	

/* Method: getClosest(i, name, names, db) */
/** This function returns the i most correlated names in the names list to "name"
* First, create a 2d array with [Name0, Correlation0 (as String)]
*							    [Name1, Correlation1 (as String)]
*							    [...  , ...					  ]
*							    [NameN, CorrelationN (as String)]
* Initialize the array with [null, 0 (as String)].  Array is i x 2.
* One by one find the correlation of each name in the list and check if it is greater than the (i-1)th element.
* If it isn't, move on to the next element.
* If it is, drop the (i-1)th element and add this element and sort the array.
*/ 
	public String[][] getCorr(int i, String name, ArrayList<String> names, NameSurferDataBase db){
		// Create main array + initialize
		String[][] arrCor = new String[i][2];
		double correlation = 0.0;
		initializeArr(arrCor);
		
		// Iterate through each of the names and add the name if it is a good match
		for (String dbName : names) {
			if (!dbName.equalsIgnoreCase(name)) {
				correlation = calcCorrelation(nsGetArray(name, db), nsGetArray(dbName, db));
				                                                                                           
				// Check to see if the correlation fits
				if ((arrCor[i-1][1] == null) || (correlation > Double.parseDouble(arrCor[i-1][1]))){
					// If it does then put it in the (i-1)th slot and sort the array
					arrCor[i-1][1] = String.valueOf(correlation);
					arrCor[i-1][0] = dbName;
					sortArrayLarge(arrCor); 
				}
			}
		}
		return arrCor;
	}
	
/* Method: getMSE(i, name, names, db) */
/** This function returns the i "closes" (smallest Mean Square Error) names list to "name"
* First, create a 2d array with [Name0, MSE0 (as String)]
*							    [Name1, MSE1 (as String)]
*							    [...  , ...					  ]
*							    [NameN, MSEN (as String)]
* Initialize the array with [null, 0 (as String)].  Array is i x 2.
* One by one find the MSE of each name in the list and check if it is less than the (i-1)th element.
* If it isn't, move on to the next element.
* If it is, drop the (i-1)th element and add this element and sort the array.
*/
	public String[][] getMSE(int i, String name, ArrayList<String> names, NameSurferDataBase db){
		// Create main array + initialize
		String[][] arrMSE = new String[i][2];
		double MSE = 0.0;
		initializeArr(arrMSE);
		
		// Iterate through each of the names and add the name if it is a good match
		for (String dbName : names) {
			if (!dbName.equalsIgnoreCase(name)) {
				MSE = calcMSE(nsGetArray(name, db), nsGetArray(dbName, db));
				
				// Check to see if the MSE fits
				if ((arrMSE[i-1][1] == null) || (MSE < Double.parseDouble(arrMSE[i-1][1]))) {
					// Sort the array
					arrMSE[i-1][1] = String.valueOf(MSE);
					arrMSE[i-1][0] = dbName;
					sortArraySmall(arrMSE); 
				}
			}
		}
		return arrMSE;
	}

/* Method: sortArraySmall(arr) */
/**
 * This is a special implementation of the Bubble Sort algorithm.
 * The entries are stored as Strings, so conversion is necessary for comparisons.
 * The Results are sorted in descending order.
 */
	private void sortArraySmall(String[][] arr){
		for(int i = 0; i< arr.length; i++) {
			for (int j = 1; j < (arr.length - i); j++){
				
				// Do necessary conversion from string to Double
				if (isSmaller(arr[j][1], arr[j-1][1])){
					swapRows(arr, j-1, j);
				}
			}
		}
	}
	
/* Method: isSmaller(a, b) */
/** 
 * This helper method returns true if a is smaller than b.
 * It takes into account the special cases where a or b are null.
 */
private boolean isSmaller(String a, String b){
	if (a == null && b == null) return false;
	if (a == null) return false;
	if (b == null) return true;
	
	return (Double.parseDouble(a) < Double.parseDouble(b));
}

/* Method: isLarger(a, b) */
/** 
 * This helper method returns true if a is larger than b.
 * It takes into account the special cases where a or b are null.
 */
private boolean isLarger(String a, String b){
	if (a == null && b == null) return false;
	if (a == null) return false;
	if (b == null) return true;
	
	return (Double.parseDouble(a) > Double.parseDouble(b));
}

/* Method: sortArrayLarge(arr) */
/**
 * This is a special implementation of the Bubble Sort algorithm.
 * The entries are stored as Strings, so conversion is necessary for comparisons.
 * The Results are sorted in descending order.
 */
	private void sortArrayLarge(String[][] arr){
		for(int i = 0; i< arr.length; i++) {
			for (int j = 1; j < (arr.length - i); j++){
				
				// Do necessary conversion from string to Double
				if (isLarger(arr[j][1], arr[j-1][1])) {
					swapRows(arr, j-1, j);
				}
			}
		}
	}

/* Method: swapRows(arr, orig, next) */
/**
 * This method takes an array, and 2 indices as arguments.
 * It swaps the entries at the indices provided.
 * This helper function is called by the sort function.
 */
	private void swapRows(String[][] arr, int orig, int next){
		String name = "";
		String number = "";
		
		// Save the originals for temporary storage
		name = arr[orig][0];
		number = arr[orig][1];
		
		// Put the new value in in the orig spot
		arr[orig][0] = arr[next][0];
		arr[orig][1] = arr[next][1];
		
		// Put the original values in the new spot
		arr[next][0] = name;
		arr[next][1] = number;
	}

/* Method: initializeArr(arrCor) */
/**
 * This method initializes the array to contain entries of:
 * [null, "0"]
 */
	private void initializeArr(String[][] arrCor){
		double zero = 0.0;
		for (int i = 0; i < arrCor.length; i++) {
			arrCor[i][0] = null;
			arrCor[i][1] = null; 
		}
	}

/* Method: nsGetArray() */
/**
 * This helper function returns an array of data associated with a name.
 */
	private Integer[] nsGetArray(String name, NameSurferDataBase db) {
		return db.findEntry(name).toArray();
	}
	
}
