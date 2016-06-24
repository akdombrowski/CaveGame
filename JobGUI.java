/*
 * Filename: JobGUI.java
 * Date: Jun 23, 2016
 * Author: Anthony Dombrowski
 * Purpose: . 
 */

import java.awt.*;
import javax.swing.*;

public class JobGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Job Progress";
	
	private final JPanel panel = new JPanel(new GridLayout(0, 1));
	
	
	
	public JobGUI() {
		// set title
		super(TITLE);
		// use border layout
		setLayout(new BorderLayout());
		// location to appear by platform
		setLocationByPlatform(true);
		// minimum size
		setMinimumSize(new Dimension(300, 300));
		// exit when close button pressed
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// set cursor to default
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		add(panel, BorderLayout.CENTER);
		
		// validate and set frame size
		validate();
		setSize(new Dimension(500, 500));
		// make visible
		setVisible(true);
	} // end constructor
	
	private void btns() {
		
	}
	
	private void actionListeners() {
//		stopBtn.addActionListener(e -> toggleStopFlag());
	}
	
	public void addJob(Job job) {
		JPanel jobPanel = new JPanel();
		// find out how to dynamically read times and status of job to continuously update progress bar
		//
		//
		//
		//
		JProgressBar prog = new JProgressBar();
		
		JButton goBtn = new JButton("Stop");
		JButton killBtn = new JButton("Cancel");
		
		goBtn.addActionListener(e -> job.toggleGoFlag());
		killBtn.addActionListener(e -> job.setKillFlag());
		
		jobPanel.add(goBtn);
		jobPanel.add(killBtn);
		
		(new Thread(job, job.worker.name+ " " + job.name)).start();
		
		panel.add(jobPanel);
	}
}
