/*
 * Filename: Treasure.java
 * Date: May 29, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 1 Treasures class. 
 */

import java.util.Scanner;

public class Treasure extends CaveElement {
	// inherits index, name
	protected Creature owner;
	protected int ownerIndex;
	protected String type;
	protected double weight;
	protected double value;

	// scanner constructor
	public Treasure(Scanner sc) {
		makeTreasure(sc);
	} // end scanner constructor
	
	// makes treasures from reading scanner
	// t:<index>:<type>:<creature>:<weight>:<value>
	public void makeTreasure(Scanner sc) {		
		// don't need first field: t
	    sc.next(); 
	    index = sc.nextInt();
	    type = sc.next();
	    ownerIndex = sc.nextInt();
	    weight = sc.nextDouble();
	    value = sc.nextDouble();
	} // end makeTreasure method
	
	public String toString() {
		// return 0 for the owner if null
		if (owner == null) {
		    return String.format("%27d : %12s : %6d : %7.1f : %7.1f", 
		    		index, type, 0, weight, value);
		} // end if belongsTo is null
		
		// return owner index as well
		return String.format("%27d : %12s : %6d : %7.1f : %7.1f", 
				index, type, owner.index, weight, value);
	} // end toString method
} // end Treasure class
