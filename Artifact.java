/*
 * Filename: Artifact.java
 * Date: June 12, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 2 Artifacts class. 
 */

import java.util.Scanner;

public class Artifact extends CaveElement {
	// inherits index, name
	// name is optional
	protected Creature owner;
	protected int ownerIndex;
	protected String type;
	
	// scanner constructor
	public Artifact(Scanner sc) {
		makeArtifact(sc);
	} // end scanner constructor
	
	// creates artifacts by reading scanner
	// a:<index>:<type>:<creature>[:<name>]
	public void makeArtifact(Scanner sc) {
	    // don't need first field: a
		sc.next ();

		// set info
	    index = sc.nextInt();
	    type = sc.next();
	    ownerIndex = sc.nextInt();
	    
	    // move to next if it has a name
	    if (sc.hasNext()) {
	    	name = sc.next ();
	    } // end if on hasNext
	} // end makeArtifact method
	
	// toString
	public String toString() {
		// check if it has an owner
		if (owner == null) {
		    return String.format("%27d : %12s : %6d :%2s%-15s", 
		    		index, type, 0, "", name);
		} // end if owner is null
		
		// return owner index if owner isn't null
		return String.format("%27d : %12s : %6d :%2s%-15s", 
				index, type, ownerIndex, "", name);
	} // end toString method
} // end Artifact class
