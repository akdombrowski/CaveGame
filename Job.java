import java.util.Scanner;

/*
 * Filename: Job.java
 * Date: Jun 23, 2016
 * Author: Anthony Dombrowski
 * Purpose: . 
 */

public class Job extends CaveElement implements Runnable {
	protected Creature worker = null;
	protected int workerIndex;
	protected int time;
	private boolean goFlag = true;
	private boolean noKillFlag = true;
	protected Status status = Status.SUSPENDED;
	
	public static enum Status {
		RUNNING, SUSPENDED, WAITING, DONE;
	} // end Status enum
	
	// scanner constructor
	public Job(Scanner sc) {
		makeJob(sc);
	} // end scanner constructor
	
	// makes treasures from reading scanner
	// j:<index>:<name>:<creature index>:<time>:
	// [:<required artifact type>:<number>]*
	public void makeJob(Scanner sc) {		
		// don't need first field: t
		sc.next();
		index = sc.nextInt();
		name = sc.next();
		workerIndex = sc.nextInt();
		time = (int) (sc.nextDouble());
	} // end makeTreasure method
	
	public void run() {
		// make instance variables so JobGUI can read them and update progress bar
		//
		//
		//
		//
		//
		//
		long time = System.currentTimeMillis();
		long startTime = time;
		long stopTime = time + 1000 * time;
		double duration = stopTime - time;
		
		synchronized(worker.party) {
			while(worker.busyFlag) {
				try {
					worker.party.wait();
				} catch(InterruptedException e) {} // end try catch
				
			}
		}
		
		while(time < stopTime && noKillFlag) {
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {} // end try catch
			
			if(goFlag) {
				status = Status.RUNNING;
				time += 100;
			} else {
				status = Status.SUSPENDED;
			}
		}
		
		// change status to done
		status = Status.DONE;
		
		synchronized(worker.party) {
			worker.busyFlag = false;
			worker.party.notifyAll();
		} // end synchronized on worker.party
	} // end run method
	
	// flips the goFlag
	public void toggleGoFlag() {
		goFlag = !goFlag;
	} // end toggleGoFlag
	
	// sets the noKillFlag to false
	public void setKillFlag() {
		noKillFlag = false;
	} // end setKillFlag
	
	// returns data in a string
	public String toString() {
		return String.format ("%7d : %15s : %7d : %5d", index, name, worker.index, time);
	} // end toString method
}
