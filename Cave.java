/*
 * Filename: Cave.java
 * Date: May 29, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 1 Cave class. 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Cave {
	// list of parties in this cave
	protected ArrayList<Party> parties = new ArrayList<Party>();
	// cave elements not owned or aligned to a party
	protected ArrayList<Creature> unalignedC = new ArrayList<Creature>();
	protected ArrayList<Treasure> unheldT = new ArrayList<Treasure>();
	protected ArrayList<Artifact> unheldA = new ArrayList<Artifact>();
	
	// maps for quicker searching
	// creature map with index key
	protected HashMap<Integer, Creature> cMap = 
			new HashMap<Integer, Creature>();
	// all elements with index, name, and type keys
	protected HashMap<Integer, CaveElement> elByIndex = 
			new HashMap<Integer, CaveElement>();
	protected HashMap<String, CaveElement> elByName = 
			new HashMap<String, CaveElement>();
	protected HashMap<String, ArrayList<CaveElement>> elByType = 
			new HashMap<String, ArrayList<CaveElement>>();

	// scanner constructor
	public Cave(Scanner sc) {
		makeCave(sc);
	} // end scanner constructor

	// action for readBtn
	public void makeCave(Scanner sc) {
		// current line
		String inline;
		// scanner on current line
		Scanner line;
		HashMap<Integer, CaveElement> elements = 
				new HashMap<Integer, CaveElement>();

		// scan through file
		while (sc.hasNext()) {
			// trims leading and trailing white space from current line
			inline = sc.nextLine().trim();

			// continue if the line is blank 
			if (inline.length() == 0) {
				continue;
			} // end if on inline's length is 0

			// compress white space also, else nextInt fails
			line = new Scanner(inline).useDelimiter ("\\s*:\\s*"); 

			// switch on beginning character too add right element
			switch (inline.charAt(0)) {
				// party
				case 'p': 
					addParty(line, elements); 
					break;
					// creature
				case 'c': 
					addCreature(line, elements); 
					break;
					// treasure
				case 't': 
					addTreasure(line, elements); 
					break;
					// artifact
				case 'a': 
					addArtifact(line, elements); 
					break;
					// anything else
				default:
					break;
			} // end switch
		} // end while reading data file
	} // end readFile method

	// add parties
	public void addParty(Scanner sc, HashMap<Integer, CaveElement> el){
		// new party
		Party prty = new Party(sc);

		// add parties to parties list and maps
		parties.add(prty);
		elByIndex.put(prty.index, prty);
		elByName.put(prty.name, prty);
	} // end method addParty

	// add creatures
	public void addCreature(Scanner sc, HashMap<Integer, CaveElement> el) {
		// new creature
		Creature c = new Creature(sc);
		// true if aligned party is in parties list
		boolean isAligned = false;
		
		// search parties for aligned party
		for(Party p : parties) {
			if(p.index == c.partyIndex) {
				p.addCreature(c);
				c.party = p;
				isAligned = true;
				break;
			} // end if party index match
		} // end for each party
		
		// add to unaligned if party wasn't found
		if(!isAligned) {
			unalignedC.add(c);
		} // end if isn't aligned
		
		// add to maps
		cMap.put(c.index, c);
		elByIndex.put(c.index, c);
		elByName.put(c.name, c);
		// add to type map
		if(!elByType.containsKey(c.type)) {
			ArrayList<CaveElement> al = new ArrayList<CaveElement>();
			al.add(c);
			elByType.put(c.type, al);
		} else {
			elByType.get(c.type).add(c); 
		} // end if, else; check if key already in map
	} // end method addToParty

	// add treasures
	public void addTreasure(Scanner sc, HashMap<Integer, CaveElement> el) {
		// new treasure
		Treasure t = new Treasure(sc);
		
		// search for owner
		Creature c = cMap.get(t.ownerIndex);
		
		// if c isn't null add owner and treasure
		if(c != null) {
			t.owner = c;
			c.addTreasure(t);
		} else {
			unheldT.add(t);
		} // end if, else on c being null
		
		// add to maps
		elByIndex.put(t.index, t);
		elByName.put(t.name, t);
		// add to type map, create new list if necessary
		if(!elByType.containsKey(t.type)) {
			ArrayList<CaveElement> al = new ArrayList<CaveElement>();
			al.add(t);
			elByType.put(t.type, al);
		} else {
			elByType.get(t.type).add(t); 
		} // end if type is in map
	} // end method addTreasure

	// add artifacts using scanner
	public void addArtifact(Scanner sc, HashMap<Integer, CaveElement> el) {
		// new artifact
		Artifact a = new Artifact(sc);

		// search for creature's party
		Creature c = cMap.get(a.ownerIndex);
		
		// check if creature belongs to a party
		if(c != null) {
			a.owner = c;
			c.addArtifact(a);
		} else {
			unheldA.add(a);
		} // end if else
		
		// add to maps
		elByIndex.put(a.index, a);
		elByName.put(a.name, a);
		// if type already exists add to list, if not create new list
		if(!elByType.containsKey(a.type)) {
			ArrayList<CaveElement> al = new ArrayList<CaveElement>();
			al.add(a);
			elByType.put(a.type, al);
		} else {
			elByType.get(a.type).add(a); 
		} // end if type is in map
	} // end method addArtifact

	// creates string of cave data
	public String toString() {
		// stringbuffer for data output
		StringBuffer sb = new StringBuffer();

		// add each category's data
		sb.append(toString(parties, "Party List"));
		sb.append(toString(unalignedC, "Creatures without a party"));
		sb.append(toString(unheldT, "Treasures without an owner"));
		sb.append(toString(unheldA, "Artifacts without an owner"));

		// return stringbuffer as string
		return sb.toString();
	} // end no-arg toString method

	// iterable and label toString
	public String toString(Iterable<? extends CaveElement> it, String label) {
		StringBuffer sb = new StringBuffer("-----" + label + "-----");

		for (CaveElement e: it) {
			sb.append("\n" + e);
		} // end for each cave element

		return sb.toString() + "\n\n";
	} // end iterable and label toString method
} // end Cave class
