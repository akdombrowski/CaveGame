/*
 * Filename: Job.java
 * Date: Jun 26, 2016
 * Author: Anthony Dombrowski
 * Purpose: Job class for project 3. Jobs to be performed by Creatures. 
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Job extends CaveElement implements Runnable {
	// gui for job progress and stop/cancel buttons
	private JPanel panel = new JPanel(new GridBagLayout());
	private JButton jbGo = new JButton("Stop");
	private JButton jbKill = new JButton("Cancel");
	private JProgressBar pm = new JProgressBar();
	
	// worker assigned to the job
	protected Creature worker = null;
	protected int workerIndex;
	// job time
	protected int time;
	
	// okay to work flags
	private boolean goFlag = true;
	private boolean noKillFlag = true;
	
	// status of job
	protected Status status = Status.SUSPENDED;
	
	// status options
	public static enum Status {
		RUNNING, SUSPENDED, WAITING, DONE;
	} // end Status enum
	
	// scanner constructor
	public Job(Scanner sc, HashMap<Integer, CaveElement> elements) {
		// get job info
		makeJob(sc, elements);
		
		// setup panel
		panelSetup();
	} // end scanner constructor
	
	// makes jobs from scanner
	// j:<index>:<name>:<creature index>:<time>:
	// [:<required artifact type>:<number>]*
	public void makeJob(Scanner sc, HashMap<Integer, CaveElement> elements) {		
		// don't need first field: t
		sc.next();
		index = sc.nextInt();
		name = sc.next();
		// worker job belongs to
		workerIndex = sc.nextInt();
		worker = (Creature) (elements.get(workerIndex));
		// job duration
		time = (int) sc.nextDouble();
	} // end makeTreasure method
	
	// GUI panel setup for jobs and their progress
	private void panelSetup() {
		// panel constraints
		GridBagConstraints c = new GridBagConstraints();
		
		// show progress string
		pm.setStringPainted(true);
		
		// shared constraints
		c.weightx = 0.0;
		c.weightx = 0.0;
		c.fill = GridBagConstraints.BOTH;
		
		// worker and job name labels
		c.gridx = 0;
		panel.add(new JLabel("Job: ", SwingConstants.TRAILING), c);
		c.gridx = 1;
		panel.add(new JLabel(name, SwingConstants.LEADING), c);
		
		c.gridx = 3;
		panel.add(new JLabel("Worker: ", SwingConstants.TRAILING), c);
		c.gridx = 4;
		panel.add(new JLabel(worker.name, SwingConstants.LEADING), c);

		// progress bar
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridx = 0;
		// progress bar
		panel.add(pm, c);
		
		// stop and cancel buttons
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		panel.add(jbGo, c);
		c.gridx = 3;
		panel.add(jbKill, c);

		// column sizes and bottom buffer space
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weighty = 1.0;
		// adds space below this row to separate from the next job
		c.insets = new Insets(0, 0, 20, 0); 
		// left column, status column
		c.ipadx = 200;
		panel.add(new JLabel(), c);
		// right column, cancel button column
		c.gridx = 3;
		panel.add(new JLabel(), c);
		// middle spacer
		c.ipadx = 100;
		c.gridwidth = 1;
		c.gridx = 2;
		panel.add(new JLabel(), c);

		// add actionlisteners to buttons
		actionListeners();
	} // end panelSetup method
	
	// action listeners for stop and cancel buttons
	private void actionListeners() {
		// switches goFlag
		jbGo.addActionListener(e -> toggleGoFlag());
		// sets noKillFlag to false
		jbKill.addActionListener(e -> setKillFlag());
	} // end actionListeners method
	
	// run method for thread
	public void run() {
		// for time calculations
		long currTime = System.currentTimeMillis();
		long startTime = currTime;
		long stopTime = currTime + 1000 * time;
		double duration = stopTime - currTime;
		
		// synch on worker's party
		synchronized(worker.party) {
			// while worker is busy
			while(worker.busyFlag) {
				
				// set status to waiting
				showStatus(Status.WAITING);
				
				// try to wait
				try {
					worker.party.wait();
				} catch(InterruptedException e) {} // end try catch
			} // end while worker busy
			
			// set worker to busy
			worker.busyFlag = true;
		} // end synchronized on worker's party
		
		// while time is before stop time and not cancelled
		while(currTime < stopTime && noKillFlag) {
			// try sleeping current thread
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {} // end try catch
			
			// set to running if okay to run
			if(goFlag) {
				// set status to running
				showStatus(Status.RUNNING);
				
				// increase time by 100
				currTime += 100;
				
				// set progress bar
				pm.setValue((int) (((currTime - startTime) / duration) * 100));
			} else {
				showStatus(Status.SUSPENDED);
			} // end if else
		} // end while
		
		// set progress bar to 100
		pm.setValue(100);
		// change status to done
		showStatus(Status.DONE);
		
		// synch on worker's party
		synchronized(worker.party) {
			worker.busyFlag = false;
			worker.party.notifyAll();
		} // end synchronized on worker.party
	} // end run method
	
	// changes color and text of jbGo button to show status
	public void showStatus(Status st) {
		// set status
		status = st;
		
		// switch on status
		switch(status) {
			case RUNNING: 
				jbGo.setBackground(Color.green);
				jbGo.setText("Running");
				break;
			case SUSPENDED: 
				jbGo.setBackground(Color.yellow);
				jbGo.setText("Suspended");
				break;
			case WAITING: 
				jbGo.setBackground(Color.orange);
				jbGo.setText("Waiting");
				break;
			case DONE: 
				jbGo.setBackground(Color.white);
				jbGo.setText("Done");
				break;
		} // end switch on status
	} // end showStatus method
	
	// flips the goFlag
	public void toggleGoFlag() {
		goFlag = !goFlag;
	} // end toggleGoFlag method
	
	// sets the noKillFlag to false
	public void setKillFlag() {
		noKillFlag = false;
		jbKill.setBackground(Color.red);
	} // end setKillFlag method
	
	// returns data in a string
	public String toString() {
		return String.format ("%7d : %15s : %7d : %5d", index, name, worker.index, time);
	} // end toString method
	
	// returns the panel
	public JPanel getPanel() {
		return panel;
	} // end getPanel method
} // end Job class
