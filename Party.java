/*
 * Filename: Party.java
 * Date: May 29, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 1 Party class. 
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Party extends CaveElement {
	// inherits index, name
	// list of creatures in this party
	protected ArrayList<Creature> creatures = new ArrayList<Creature>();
	
	// scanner constructor
	public Party(Scanner sc) {
		makeParty(sc);
	} // end scanner constructor
	
	// makes a party reading a scanner
	// p:<index>:<name>
	public void makeParty(Scanner sc) {
		// don't need the first field: p
		sc.next();
		index = sc.nextInt();
		name = sc.next();
	} // end makeParty method
	
	// adds a creature to this party
	public void addCreature(Creature c) {
		creatures.add(c);
	} // end addCreature method
	
	// searches for a creature by its index
	public ArrayList<Creature> findByIndex(int i) {
		// list for results
		ArrayList<Creature> results = new ArrayList<Creature>();
		
		// search each of the creatures in this party
		for(Creature c : creatures) {
			if(c.index == i) {
				results.add(c);
			} // end if index equals i
		} // end for each creature
		
		// return results list
		return results;
	} // end findByIndex method
	
	// searches for a creature by name
	public ArrayList<Creature> findByName(String n) {
		// results list
		ArrayList<Creature> results = new ArrayList<Creature>();

		// search each creature in this party
		for(Creature c : creatures) {
			if(c.name.equalsIgnoreCase(n)) {
				results.add(c);
			} // end if index equals i
		} // end for each creature
		
		// return results list
		return results;
	} // end findByName method
	
	// searches for a creature by type
	public ArrayList<Creature> findByType(String t) {
		// search results list
		ArrayList<Creature> results = new ArrayList<Creature>();

		// search each creature in this party
		for(Creature c : creatures) {
			if(c.type.equalsIgnoreCase(t)) {
				results.add(c);
			} // end if index equals i
		} // end for each creature

		// return results list
		return results;
	} // end findByType method
	
	// returns the creatures in this party as a string
	public String toString() {
		StringBuffer sb = new StringBuffer(String.format(
				"%20s %8s Creatures %n%n", name, "(" + index + ")"));
		
		// add each creature
		for(Creature c : creatures) {
			sb.append(c + "\n");
		} // end for each creature
		
		// convert to string and return
		return sb.toString();
	} // end method to string
} // end Party class
