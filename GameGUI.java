/*
 * Filename: GameGUI.java
 * Date: May 29, 2016
 * Author: Anthony Dombrowski
 * Purpose: Project 1 GUI class. 
 */

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class GameGUI extends JFrame {
	// serial version: 123L
	private static final long serialVersuionUID = 123L;
	
	// cave to create when reading file
	private Cave cave;
	
	// data text output, and search text input
	private final JTextArea jta = new JTextArea("");
	private final JTextField txtFld = new JTextField(10);
	
	// read, search, and display buttons
	private final JButton readBtn = new JButton("Read");
	private final JButton searchBtn = new JButton("Search");
	private final JButton dispBtn = new JButton("Display");
	
	// file chooser to pick data file
	private final JFileChooser chooseFile = new JFileChooser(".");
	
	// combo box and radio buttons group for search criteria
	private final JComboBox<String> combo = new JComboBox<String>();
	
	// tree with scroll pane
	private JTree tree;
	private JScrollPane treeScroll;
	
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
		setMinimumSize(new Dimension(600, 500));
		// exit when close button pressed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// make visible
		setVisible(true);
		
		// setup for action listeners
		actnListeners();
		// setup for text area
		jtaSetup();
		// setup for panel at top of frame
		topPanelSetup();
		// main output, tree and data text
		splitPaneSetup();
		
		setSize(new Dimension(800, 500));
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
	
	// adds btns to a panel
	private JPanel btnsSetup() {
		// panel to add components, return at end
		JPanel panel = new JPanel(new GridBagLayout());
		// constraints for adding components to the panel
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel read = new JLabel("Choose data file: ");
		JLabel disp = new JLabel("Display entire cave: ");
		
		// constraints
		c.weightx = 1.0;
		
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
		
		// return the panel with read and display buttons
		return panel;
	} // end btnsSetup method
	
	// sets up the search panel with label, text field, and combo box
	private JPanel searchBarSetup() {
		// panel to add items, return at end
		JPanel panel = new JPanel(new GridBagLayout());
		// constraints for adding components to the panel
		GridBagConstraints c = new GridBagConstraints();
		
		// search label
		final JLabel searchLabel = new JLabel("Search Target: ");
		
		// add name and index buttons to group
		combo.addItem("Index");
		combo.addItem("Name");
		combo.addItem("Type");
		
		// constraints
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridy = 0;
		
		// add search label first
		c.anchor = GridBagConstraints.BASELINE_TRAILING;
		c.gridx = 0;
		panel.add(searchLabel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE_LEADING;
		// text field for searching in middle
		c.gridx = 1;
		panel.add(txtFld, c);
		
		c.gridx = 2;
		panel.add(combo, c);
		
		// search button on furthest right
		c.gridx = 3;
		panel.add(searchBtn, c);
		
		// return the search bar panel
		return panel;
	} // end searchBarSetup method
	
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
	
	// adds the top panel to the frame
	private void topPanelSetup() {
		// top panel for components above main text area
		JPanel panel = new JPanel(new GridBagLayout());
		// constraints for adding components to the panel
		GridBagConstraints c = new GridBagConstraints();
		
		// get the button and search panels
		JPanel btnsPanel = btnsSetup();
		JPanel searchPanel = searchBarSetup();
		
		// constraints
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 0.5;
		c.gridx = 0;

		// btn panel on top
		c.gridy = 0;
		panel.add(btnsPanel, c);
		
		// seach panel below buttons, on top of text area
		c.gridy = 1;
		panel.add(searchPanel, c);
		
		// return panel
		add(panel, BorderLayout.NORTH);
	} // end topPanelSetup method
	
	// add action listeners to buttons
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
				(String) combo.getSelectedItem(), txtFld.getText()));
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
		} // end if else
	} // end displayCave method
	
	// action for search button
	private void search(String type, String target) {
		if(cave != null) {
			// title and message
			String title = "Search Error";
			String msg = "Please try again.";
			
			// search for target element
			CaveElement e = null;
			ArrayList<CaveElement> al = null;
			
			// switch on type to get right search category
			switch(type) {
				case "Index":
					try {
						// try to convert input to an int
						int ind = Integer.parseInt(target);
						// search for the index
						e = cave.elByIndex.get(ind);
					} catch(NumberFormatException ex) {
						// error message reading input as an int
						title += ": Index Input Read Error";
					} // end try catch
					break;
				case "Name":
					// search for element by name
					e = cave.elByName.get(target);
					break;
				case "Type":
					// search for element by type
					al = cave.elByType.get(target);
					break;
				default:
					break;
			} // end switch on type
			
			// if the element is found and is of the right class
			if(e != null) {
				title = target + " was found ";
				msg = e.toString();
			} else if(al != null) {
				title = target + " was found ";
				msg = ""; // reset message to get rid of error message
				
				// add each element in the list
				for(CaveElement c : al) {
					msg += c.toString() + "\n";
				} // end for each cave element in the array list
			} else {
				// couldn't find
				title = target + " " + type + " couldn't be found ";
			} // end if, else on e being null && the right type
			
			// set display text to 
			jta.setText(title + "\n\n" + msg);
		} else {
			// error message if cave is null
			jta.setText("Search Error: Empty Cave");
		} // end if else on cave being null
	} // end search method
	
	// create nodes for multi tree data structure
	private DefaultMutableTreeNode createNodes(String title) {
		// root node
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(title);
		// party node
		DefaultMutableTreeNode partyNode;
		// creature node
		DefaultMutableTreeNode creatureNode;
		
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
	
	// main
	public static void main(String[] args) {
		new GameGUI();
	} // end main method
} // end GameGUI class
