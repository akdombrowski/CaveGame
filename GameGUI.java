/*
 * Filename: GameGUI.java
 * Date: June 26, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 3 GUI class for the Cave game. 
 */

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class GameGUI extends JFrame {
	// serial version
	private static final long serialVersionUID = 1L;

	// cave to create when reading file
	private Cave cave;
	
	// data text output, and search text input
	private final JTextArea jta = new JTextArea("");
	private final JTextField txtFld = new JTextField(10);
	
	// read, search, and display buttons
	private final JButton readBtn = new JButton("Read");
	private final JButton searchBtn = new JButton("Search");
	private final JButton dispBtn = new JButton("Display");
	private final JButton sortBtn = new JButton("Sort");
	private final JButton dispJobs = new JButton("Show Jobs");
	
	// file chooser to pick data file
	private final JFileChooser chooseFile = new JFileChooser(".");
	
	// searchCombo box and radio buttons group for search criteria
	private final JComboBox<String> searchCombo = new JComboBox<String>();
	// element to sort: Creature or Treasure
	private final JComboBox<String> sortElCombo = new JComboBox<String>();
	// category to sort element by
	private final JComboBox<String> sortCatCombo = new JComboBox<String>();
	
	// tree with scroll pane
	private JTree tree;
	private JScrollPane treeScroll;
	
	// scroll pane for jobs panel
	private JScrollPane jobScroll;
	
	// split pane to hold tree and data output
	private JSplitPane splitPane;
	
	// no argument default constructor
	public GameGUI() {
		// set title
		super("Cave Game");
		// use border layout
		setLayout(new BorderLayout());
		// location to appear by platform
		setLocationByPlatform(true);
		// minimum size
		setMinimumSize(new Dimension(850, 300));
		// exit when close button pressed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// set cursor to default
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		// setup for action listeners
		actnListeners();
		// setup for text area
		jtaSetup();
		// setup for panel at top of frame
		topPanelSetup();
		// main output, tree and data text
		splitPaneSetup();
		
		// validate and set frame size
		validate();
		setSize(new Dimension(900, 900));
		// make visible
		setVisible(true);
	} // end default constructor
	
	// adds tree and data to a split pane and adds to center of frame
	private void splitPaneSetup() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				treeSetup(), jtaSetup());
		splitPane.setDividerLocation(200);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.2);
		
		add(splitPane, BorderLayout.CENTER);
		displayCave();
	} // end mainOutput method
	
	// adds text to a scroll pane and puts it in a JPanel
	private JPanel jtaSetup() {
		// panel to add text area to, return at end
		JPanel panel = new JPanel(new BorderLayout());
		// add scrolling to text area
		JScrollPane jsp = new JScrollPane(jta);
		// make uneditable
		jta.setEditable(false);
		// set monospaced font
		jta.setFont(new Font("Monospaced", 0, 14));
		jta.setBackground(Color.WHITE);
		// add to the center of the main frame
		panel.add(jsp, BorderLayout.CENTER);
		
		// return panel
		return panel;
	} // end jtaSetup method
	
	// setup for entire top panel
	private void topPanelSetup() {
		// panel to add items, return at end
		JPanel panel = new JPanel(new GridBagLayout());
		// constraints for adding components to the panel
		GridBagConstraints c = new GridBagConstraints();

		// sort labels
		final JLabel sortLabel = new JLabel("Sort: ");

		// search label
		final JLabel searchLabel = new JLabel("Search Target: ");

		// read, display labels
		final JLabel read = new JLabel("Choose data file: ");
		final JLabel disp = new JLabel("Display entire cave: ");
		
		// read, display row
		// constraints
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		// read and display buttons
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		panel.add(readBtn, c);
		c.gridx = 3;
		panel.add(dispBtn, c);

		// read and display labels
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.BASELINE_TRAILING;
		c.gridx = 0;
		panel.add(read, c);
		c.gridx = 2;
		panel.add(disp, c);
		
		// search row
		// add name and index buttons to group
		searchCombo.addItem("Index");
		searchCombo.addItem("Name");
		searchCombo.addItem("Type");
				
		// search row constraints
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridy = 1;

		// add search label first
		c.anchor = GridBagConstraints.BASELINE_TRAILING;
		c.gridx = 0;
		panel.add(searchLabel, c);

		// text field
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		// text field for searching in middle
		c.gridx = 1;
		panel.add(txtFld, c);

		// search searchCombo box
		c.gridx = 2;
		panel.add(searchCombo, c);

		// search button on furthest right
		c.gridx = 3;
		panel.add(searchBtn, c);
		
		// add name and index buttons to group
		sortElCombo.addItem("Creature");
		sortElCombo.addItem("Treasure");

		// sort row constraints
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.BASELINE_TRAILING;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridy = 2;
		c.gridx = 0;
		panel.add(sortLabel, c);
		
		// sort element
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		c.gridx = 1;
		panel.add(sortElCombo, c);

		// sortBy category
		c.gridx = 2;
		panel.add(sortCatCombo, c);

		// sort button on furthest right
		c.gridx = 3;
		panel.add(sortBtn, c);
		
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 4;
		panel.add(dispJobs, c);

		// add to main layout
		add(panel, BorderLayout.NORTH);
	} // end topPanelSetup method
	
	// returns panel with the JTree in a scrollpane
	private JPanel treeSetup() {
		JPanel panel = new JPanel(new BorderLayout());
		
		// check if tree is null
		if(tree == null) {
			// make a default tree to initially appear
			tree = new JTree(new DefaultMutableTreeNode("Emtpy Tree"));
		} // end if tree is null
		
		// add scrolling to tree, and add to panel
		treeScroll = new JScrollPane(tree);
		panel.add(treeScroll, BorderLayout.CENTER);

		// return the panel with scrolling tree
		return panel;
	} // end treeSetup method
	
	// add action listeners to buttons
	@SuppressWarnings("unchecked") // for sort category combo box
	private void actnListeners() {		
		// action listener for the read button
		readBtn.addActionListener(e -> {
			chooseFile.showDialog(null, "Select Data File");
			
			// try reading in the file and creating the cave
			try(Scanner inputFileSc = 
					new Scanner(chooseFile.getSelectedFile())) {
				// read file, create cave using scanner constructor
				readFile(inputFileSc);
				
				// create new tree
				tree = new JTree(createNodes("Cave: \"" + 
						chooseFile.getSelectedFile().getName() + "\""));
				
				// panel for new tree
				JPanel trP = treeSetup();
				// reset tree component in split pane
				splitPane.setLeftComponent(trP);
				// display Cave data 
				displayCave();
			} catch (FileNotFoundException | NullPointerException ex) {
				// display error message and ask for positive integer
				JOptionPane.showMessageDialog(null, 
						"Error reading file. "
						+ "Please try again or choose another file.", 
						"File Read Error", 
						JOptionPane.ERROR_MESSAGE);
			} // end try catch
		}); // end lambda expression, add actionListener
			
		// action listener for display button
		dispBtn.addActionListener(e -> displayCave());
		
		// action listener for search button
		searchBtn.addActionListener(e -> search(
				(String) searchCombo.getSelectedItem(), txtFld.getText()));
		
		// action listener for sort button
		sortBtn.addActionListener(e -> sort(sortCatCombo.getSelectedItem()));
		
		// item listener for sort element combo box
		sortElCombo.addItemListener(e -> {
			// get sort by element (Creature or Treasure)
			String selected = (String) sortElCombo.getSelectedItem();
			@SuppressWarnings("rawtypes") // type will be chosen based on sort
			DefaultComboBoxModel model;
			
			// sort categories based on element selected
			if(selected.equals("Treasure")) {
				// array of sortby categories for treasure
				Treasure.SORTBY[] cat = Treasure.SORTBY.values();
				// create a new model of treasure sort categories
				model = new DefaultComboBoxModel<Treasure.SORTBY>(cat);
				// set model
				sortCatCombo.setModel(model);
			} else {
				// array of sortby categories for creature
				Creature.SORTBY[] cat = Creature.SORTBY.values();
				// new model with creature sort categories
				model = new DefaultComboBoxModel<Creature.SORTBY>(cat);
				// set model
				sortCatCombo.setModel(model);
			} // end if else
		}); // end lambda expression, add ItemListener to sortElCombo
		
		// action listener for display jobs button
		dispJobs.addActionListener(e -> {
			jobFrame();
		}); // end lambda expression, addAction listener to dispJobs
	} // end actnListeners method
	
	// action for readBtn
	private void readFile(Scanner sc) {
		cave = new Cave(sc);
	} // end readFile method
	
	// action for display button
	private void displayCave() {
		if(cave == null) {
			jta.setText("Empty Cave");
		} else {
			jta.setText("" + cave);
			
			jobFrame();
		} // end if else
	} // end displayCave method
	
	// action for search button
	private void search(String type, String target) {
		if(cave != null) {
			// title and message
			String title = "Search failed.";
			String msg = "Please try again.";
			
			// array list for search results
			ArrayList<CaveElement> al = null;
			
			// switch on type to get right search category
			switch(type) {
				case "Index":
					try {
						// try to convert input to an int
						int ind = Integer.parseInt(target);
						// search for the index
						al = cave.searchByIndex(ind);
					} catch(NumberFormatException ex) {
						// error message reading input as an int
						title += "\nIndex Input Read Error";
					} // end try catch
					break;
				case "Name":
					// search for element by name
					al = cave.searchByName(target);
					break;
				case "Type":
					// search for element by type
					al = cave.searchByType(target);
					break;
				default:
					break;
			} // end switch on type
			
			// set title and message based on search results
			if(al != null && !al.isEmpty()) {
				title = target + " was found ";
				msg = ""; // reset message to get rid of error message
				
				// add each element in the list
				for(CaveElement c : al) {
					msg += c.toString() + "\n";
				} // end for each cave element in the array list
			} else {
				// couldn't find
				title += "\n" + type + ": " + target + " - couldn't be found ";
			} // end if, else on e being null && the right type
			
			// set display text to the title and msg strings
			jta.setText(title + "\n\n" + msg);
		} else {
			// error message if cave is null
			jta.setText("Search Error: Empty Cave");
		} // end if else on cave being null
	} // end search method
	
	// sorts creatures or treasures by specified category from combo box
	private void sort(Object obj) {
		// sort each creature list or each treasure list for each creature
		for(Party p : cave.parties) {
			// check if obj is a creature or treasure sortby category
			if(obj instanceof Creature.SORTBY) {
				// set sortby category of Creature class
				Creature.sortBy = (Creature.SORTBY) obj; 
				// sort creatures of party p
				Collections.sort(p.creatures);	
			} else {
				// sort each treasure list for each creature
				for(Creature c : p.creatures) {
					// set sortby category of Treasure class
					Treasure.sortBy = (Treasure.SORTBY) obj;
					// sort treasures of creature c
					Collections.sort(c.treasures);
				} // end for each creature
			} // end if else on obj class type
		} // end for each party
		
		// create new tree
		tree = new JTree(createNodes("Cave: \"" + 
				chooseFile.getSelectedFile().getName() + "\""));
		// redisplay main area: tree and output text
		remove(splitPane);
		splitPaneSetup();
		validate();
	} // end sort method
	
	// create nodes for multi tree data structure
	private DefaultMutableTreeNode createNodes(String title) {
		// root node
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		// party node
		DefaultMutableTreeNode partyNode;
		// creature node
		DefaultMutableTreeNode creatureNode;
		// job node
		DefaultMutableTreeNode jobNode;
		
		// create nodes for each party and add creatures with treasures, artifacts
		for(Party p : cave.parties) {
			// new party node
			partyNode = new DefaultMutableTreeNode(p.name);
			// add party node to the root's children
			top.add(partyNode);
			
			// create creature nodes 
			for(Creature c : p.creatures) {
				// new creature node
				creatureNode = new DefaultMutableTreeNode(c.name);
				// add creature node to party's children
				partyNode.add(creatureNode);
				
				// add treasures
				for(Treasure t : c.treasures) {
					creatureNode.add(new DefaultMutableTreeNode("T: " + t.type));
				} // end for each treasure
				
				// add artifacts
				for(Artifact a : c.artifacts) {
					creatureNode.add(new DefaultMutableTreeNode("A: " + a.type));
				} // end for each artifact
			} // end for each creature
		} // end for each party
		
		// return root node
		return top;
	} // end createNodes method
	
	// creates panel with jobs
	private void jobsPanel() {
		// new grid layout with nonzero vertical gaps
		GridLayout gl = new GridLayout(0, 1);
		gl.setVgap(10);
		
		// new gridlayout panel and scroll pane
		JPanel panel = new JPanel(gl);
		jobScroll = new JScrollPane(panel);
		
		// check if cave is null
		if(cave != null) {
			// add each job to the panel
			for(Job j : cave.jobs) {
				if(j == null) {
					System.out.println("null job");
				}
				panel.add(j.getPanel());
			} // end for each job in jobs list
		} // end if cave isn't null
	} // end jobsPanel
	
	private void jobFrame() {
		JFrame frame = new JFrame("Jobs");
		// use border layout
		frame.setLayout(new BorderLayout());
		// location to appear by platform
		frame.setLocationByPlatform(true);
		// exit when close button pressed
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// set cursor to default
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		// setup jobsPanel
		jobsPanel();
		// add job scroll pane
		frame.add(jobScroll, BorderLayout.CENTER);
		// get preffered size and validate
		frame.pack();
		// make visible
		frame.setVisible(true);
	} // end jobFrame method
	
	// main
	public static void main(String[] args) {
		new GameGUI();
	} // end main method
} // end GameGUI class
