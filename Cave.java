/*
 * Filename: Cave.java
 * Date: June 26, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 3 Cave class. 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Cave {
	// list of parties in this cave
	protected ArrayList<Party> parties = new ArrayList<Party>();
	// list of jobs
	protected ArrayList<Job> jobs = new ArrayList<Job>();
	// cave elements not owned or aligned to a party
	protected ArrayList<Creature> unalignedC = new ArrayList<Creature>();
	protected ArrayList<Treasure> unheldT = new ArrayList<Treasure>();
	protected ArrayList<Artifact> unheldA = new ArrayList<Artifact>();

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

			// switch on beginning character to add right element
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
				// job
				case 'j':
					addJob(line, elements);
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
		el.put(prty.index, prty);
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
		
		// add creature to elements map
		el.put(c.index, c);
	} // end method addToParty

	// add treasures
	public void addTreasure(Scanner sc, HashMap<Integer, CaveElement> el) {
		// new treasure
		Treasure t = new Treasure(sc);
		
		// search for owner
		CaveElement e = el.get(t.ownerIndex);
		Creature c;
		
		// if c isn't null add owner and treasure
		if(e != null && e instanceof Creature) {
			c = (Creature) e;
			t.owner = c;
			c.addTreasure(t);
		} else {
			unheldT.add(t);
		} // end if, else on c being null
		
		// add treasure to elements map
		el.put(t.index, t);
	} // end method addTreasure

	// add artifacts using scanner
	public void addArtifact(Scanner sc, HashMap<Integer, CaveElement> el) {
		// new artifact
		Artifact a = new Artifact(sc);

		// search for owner
		CaveElement e = el.get(a.ownerIndex);
		Creature c;
		
		// check for owner
		if(e != null && e instanceof Creature) {
			c = (Creature) e;
			a.owner = c;
			c.addArtifact(a);
		} else {
			unheldA.add(a);
		} // end if else
		
		// add artifact to elements map
		el.put(a.index, a);
	} // end method addArtifact
	
	// create new job
	public void addJob(Scanner sc, HashMap<Integer, CaveElement> el) {
		// create new job
		Job j = new Job(sc, el);
		
		// start a new job thread
		(new Thread(j)).start();
		
		// add job to jobs list
		jobs.add(j);
	} // end addJob method
	
	// search through parties by index, if doesn't match a party, 
	// call each party's search method
	public ArrayList<CaveElement> searchByIndex(int index) {
		// search parties first
		ArrayList<CaveElement> ceList = findByIndex(index);
		
		// check if list is empty
		if(ceList.isEmpty()) {
			for(Party p : parties) {
				ceList = p.searchByIndex(index);
				if(!ceList.isEmpty()) {
					return ceList;
				} // end if ceList isn't empty
			} // end for each party
		} // end if else ceList isn't empty from findByIndex
		
		// if above search hasn't returned a match check unassigned elements
		// unassigned creatures
		for(CaveElement ce : unalignedC) {
			if(ce.index == index) {
				ceList.add(ce);
			} // end if index matches
		} // end for each element in unalignedC
		// unassigned treasures
		for(CaveElement ce : unheldT) {
			if(ce.index == index) {
				ceList.add(ce);
			} // end if index matches
		} // end for each element in unheldT
		// unassigned artifacts
		for(CaveElement ce : unheldA) {
			if(ce.index == index) {
				ceList.add(ce);
			} // end if index matches
		} // end for each element in unheldA
		
		// return ceList, might be empty 
		return ceList;
	} // end search method
	
	// searches for a name
	public ArrayList<CaveElement> searchByName(String name) {
		// search parties first
		ArrayList<CaveElement> ceList = findByName(name);
	
		// check if list is empty
		if(ceList.isEmpty()) {
			for(Party p : parties) {
				ceList = p.searchByName(name);
				if(!ceList.isEmpty()) {
					return ceList;
				} // end if ceList isn't empty
			} // end for each party
		} // end if else ceList isn't empty from findByIndex
		
		// if above search hasn't returned a match check unassigned elements
		// unassigned creatures
		for(CaveElement ce : unalignedC) {
			if(ce.name == name) {
				ceList.add(ce);
			} // end if name matches
		} // end for each element in unalignedC
		// unassigned treasures
		for(CaveElement ce : unheldT) {
			if(ce.name == name) {
				ceList.add(ce);
			} // end if name matches
		} // end for each element in unheldT
		// unassigned artifacts
		for(CaveElement ce : unheldA) {
			if(ce.name == name) {
				ceList.add(ce);
			} // end if name matches
		} // end for each element in unheldA
		
		return ceList;
	} // end searchByName method

	// searches for a type
	public ArrayList<CaveElement> searchByType(String type) {
		ArrayList<CaveElement> ceList = new ArrayList<CaveElement>();
		
		// search within each party for the type
		for(Party p : parties) {
			// search each party for the type
			ArrayList<CaveElement> list = p.searchByType(type);
			
			// if the list isn't empty add items
			if(!list.isEmpty()) {
				// add each of the items in list to ceList
				for(CaveElement ce : list) {
					ceList.add(ce);
				} // end for each cave element in list
			} // end if
		} // search for type
		
		// if above search hasn't returned a match check unassigned elements
		// unassigned creatures
		for(Creature c : unalignedC) {
			if(c.type == type) {
				ceList.add(c);
			} // end if type matches
		} // end for each element in unalignedC
		// unassigned treasures
		for(Treasure t : unheldT) {
			if(t.type == type) {
				ceList.add(t);
			} // end if type matches
		} // end for each element in unheldT
		// unassigned artifacts
		for(Artifact a : unheldA) {
			if(a.type == type) {
				ceList.add(a);
			} // end if type matches
		} // end for each element in unheldA
		
		return ceList;
	} // end searchByType method

	// searches for a creature by its index
	public ArrayList<CaveElement> findByIndex(int index) {
		// list for results
		ArrayList<CaveElement> results = new ArrayList<CaveElement>();

		// search each of the parties in this party
		for(Party p : parties) {
			if(p.index == index) {
				results.add(p);
			} // end if index equals index
		} // end for each creature

		// return results list
		return results;
	} // end findByIndex method

	// searches for a creature by name
	public ArrayList<CaveElement> findByName(String name) {
		// results list
		ArrayList<CaveElement> results = new ArrayList<CaveElement>();

		// search each creature in this party
		for(Party p : parties) {
			if(p.name.equalsIgnoreCase(name)) {
				results.add(p);
			} // end if index equals index
		} // end for each creature

		// return results list
		return results;
	} // end findByName method

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
