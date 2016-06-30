/*
 * Filename: CaveElement.java
 * Date: June 26, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 3 - Superclass for elements in the cave: creature, 
 * artifact, and treasure. Elements may not belong to any party.
 */

public class CaveElement {
	protected String name = "";
	protected int index = 0;
	
	// no-arg default constructor
	public CaveElement() {
		name = "No-name";
		index = 0;
	} // end no-arg default constructor
	
	// name constructor
	public CaveElement(String n) {
		name = n;
		index = 0;
	} // end name constructor
	
	// name and index constructor
	public CaveElement(String n, int i) {
		name = n;
		index = i;
	} // end name and index constructor
	
	// returns name of element
	public String getName() {
		return name;
	} // end getName method
	
	// returns index of element
	public int getIndex() {
		return index;
	} // end getIndex method
	
	// returns name and index
	public String toString() {
		return "CaveElement: " + name + "(" + index + ")";
	} // end toString method
} // end CaveElement class
