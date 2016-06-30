/*
 * Filename: Treasure.java
 * Date: June 26, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 3 Treasures class. 
 */

import java.util.Scanner;

public class Treasure extends CaveElement implements Comparable<Treasure> {
	// inherits index, name
	protected Creature owner;
	protected int ownerIndex;
	protected String type;
	protected double weight;
	protected double value;

	// sorting category variable, default to weight
	protected static SORTBY sortBy = SORTBY.WEIGHT;

	// enum for sorting categories
	public enum SORTBY {
		WEIGHT, VALUE;
	} // end SORTBY enum
	
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
	
	// returns information as a string
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
	
	// compares this treasure to the treasure parameter based on sortBy
	@Override
	public int compareTo(Treasure t) {
		// return 0 if t is null
		if(t == null) {
			return 0;
		} // end if
		
		// switch on sortBy
		switch(sortBy) {
			case WEIGHT: 
				return (int) Math.ceil(weight - t.weight);
			case VALUE:
				return (int) Math.ceil(value - t.value);
			default:
			 	return 0;
		} // end switch
	} // end compareTo method
} // end Treasure class
