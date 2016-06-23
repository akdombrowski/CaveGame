/*
 * Filename: Party.java
 * Date: June 12, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 2 Party class. 
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
	
	// search for the index
	public ArrayList<CaveElement> searchByIndex(int index) {
		// list for results
		ArrayList<CaveElement> ceList = findByIndex(index);
		
		if(ceList.isEmpty()) {
			for(Creature c : creatures) {
				ceList = c.searchByIndex(index);
				if(!ceList.isEmpty()) {
					return ceList;
				} // end if empty search results
			} // end for each creature
		} // end if else on cList size greater than 0
		
		// return empty search msg if method doesn't return earlier
		return ceList;
	} // end search method
	
	// search for a name
	public ArrayList<CaveElement> searchByName(String name) {
		// list for results
		ArrayList<CaveElement> ceList = findByName(name);
		
		// if name wasn't found in creatures list
		if(ceList.isEmpty()) {
			// run each creature's search method 
			for(Creature c : creatures) {
				ceList = c.searchByName(name);
				if(!ceList.isEmpty()) {
					return ceList;
				} // end if empty search results
			} // end for each creature
		} // end if else on ceList is empty
		
		return ceList;
	} // end searchByName method
	
	// search for a type
	public ArrayList<CaveElement> searchByType(String type) {
		// list for results
		ArrayList<CaveElement> ceList = findByType(type);

		// if name wasn't found in creatures list
		if(ceList.isEmpty()) {
			// run each creature's search method 
			for(Creature c : creatures) {
				ceList = c.searchByType(type);
				if(!ceList.isEmpty()) {
					return ceList;
				} // end if empty search results
			} // end for each creature
		} // end if else on ceList is empty

		return ceList;
	} // end searchByType method
	
	// searches for a creature by its index
	public ArrayList<CaveElement> findByIndex(int i) {
		// list for results
		ArrayList<CaveElement> results = new ArrayList<CaveElement>();
		
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
	public ArrayList<CaveElement> findByName(String n) {
		// results list
		ArrayList<CaveElement> results = new ArrayList<CaveElement>();

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
	public ArrayList<CaveElement> findByType(String t) {
		// search results list
		ArrayList<CaveElement> results = new ArrayList<CaveElement>();

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
