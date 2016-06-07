/*
 * Filename: Creature.java
 * Date: May 29, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 1 Creature class. 
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Creature extends CaveElement {
	// inherits index, name
	// lists for artifacts and treasures this creature has
	protected ArrayList<Artifact> artifacts = new ArrayList<Artifact>();
	protected ArrayList<Treasure> treasures = new ArrayList<Treasure>();
	
	// creature's party
	protected Party party;
	protected int partyIndex;
	// creature info
	protected int empathy;
	protected int fear;
	protected String type;
	protected double capacity;
	
	// scanner constructor
	public Creature(Scanner sc) {
		makeCreature(sc);
	} // end scanner constructor
	
	// makes creatures by reading scanner
	// c:<index>:<type>:<name>:<party>:<empathy>:<fear>:<carrying capacity>
	public int makeCreature(Scanner sc) {
		// don't need first field: c
		sc.next (); 
		// get creature info
	    index = sc.nextInt();
	    type = sc.next();
	    name = sc.next();
	    partyIndex = sc.nextInt();
	    empathy = sc.nextInt();
	    fear = sc.nextInt();
	    capacity = sc.nextDouble();
	    
	    // return the party index
	    return partyIndex;
	} // end makeCreature method
	
	// adds a treasure to treasures list
	public void addTreasure(Treasure t) {
		treasures.add(t);
	} // end addTreasure method
	
	// adds an artifact to artifacts list
	public void addArtifact(Artifact a) {
		artifacts.add(a);
	} // end addArtifact method
	
	// search by index for a treasure or artifact
	public ArrayList<CaveElement> findByIndex(int i) {
		ArrayList<CaveElement> cList = new ArrayList<CaveElement>();
		
		for(Treasure t : treasures) {
			if(t.index == i) {
				cList.add(t);
			} // end if index equals i
		} // end for each treasure
		
		for(Artifact a : artifacts) {
			if(a.index == i) {
				cList.add(a);
			} // end if index match
		} // end for each artifact
		
		return cList;
	} // end findByIndex method
	
	// searches for a named treasure or artifact
	public ArrayList<CaveElement> findByName(String n) {
		// results list
		ArrayList<CaveElement> cList = new ArrayList<CaveElement>();
		
		// search each treasure by name
		for(Treasure t : treasures) {
			if(t.name.equalsIgnoreCase(n)) {
				cList.add(t);
			} // end if name match
		} // end for each treasure
		
		// search each artifact by name
		for(Artifact a : artifacts) {
			if(a.name.equalsIgnoreCase(n)) {
				cList.add(a);
			} // end if name match
		} // end for each artifact

		// return results list
		return cList;
	} // end findByName method
	
	// search by type for a treasure or string
	public ArrayList<CaveElement> findByType(String ty) {
		// list to hold search results
		ArrayList<CaveElement> cList = new ArrayList<CaveElement>();
		
		for(Treasure t : treasures) {
			if(t.type.equalsIgnoreCase(ty)) {
				cList.add(t);
			} // end if name match
		} // end for each treasure
		
		for(Artifact a : artifacts) {
			if(a.type.equalsIgnoreCase(ty)) {
				cList.add(a);
			} // end if name match
		} // end for each artifact

		return cList;
	}
	
	// to String method
	public String toString() {
		// store results in stringbuffer
		StringBuffer sb = new StringBuffer(String.format("%18s - ", name));
				
		// check if creature is aligned to a party
		if(party == null) {
			sb.append(String.format("%6d : %12s : %6d : %7d : %6d : %7.1f", 
		    		index, type, 0, empathy, fear, capacity));
		} else {
			sb.append(String.format("%6d : %12s : %6d : %7d : %6d : %7.1f", 
		    		index, type, partyIndex, empathy, fear, capacity));
		} // end if, else on party being null
		
		sb.append(String.format("%n%20s%n", "Artifacts:"));
		
		// add each artifact
		for(Artifact a : artifacts) {
			sb.append(a + "\n");
		} // end for each artifact
		
		// add Treasures title
		sb.append(String.format("%20s%n", "Treasures:"));
		
		// add each treasure
		for(Treasure t : treasures) {
			sb.append(t + "\n");
		} // end for each treasure
		
		// convert to string and return stringbuffer
		return sb.toString();
	} // end toString method
} // end Creature class
