package a2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Photorenamer extends JFrame implements ActionListener {
		
	/**
	 * @author Shoaib Khan
	 * 
	 * @author Daniel Visca
	 * 
	 * Initializes the PhotoRenamer program and displays the GUI, allowing the user to choose
	 * the directory, add tags, remove tags, view all tags, view the history and open up the log file.
	 */
	Panel PanelLeft = new Panel();;
	static PanelImage PanelImage1 = new PanelImage();
	static DefaultListModel dlm = new DefaultListModel();
	static JList list = new JList(dlm);
	static int numV;
	
	ListSelectionModel lsm = new DefaultListSelectionModel();
	JScrollPane scroller = new JScrollPane(list);
	JTextArea info = new JTextArea(10,10);
	
	private static final long serialVersionUID = 1L;
	public int len_list = 0;
	
	
	public Photorenamer() {
		
		super("PhotoRenamer");
	
		// Initializing the parameters of the pop up window
		setSize(900, 600);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			
		// Creating the different buttons which the user can select
	
		Panel PanelTop = new Panel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		JScrollPane scrollerInfo = new JScrollPane(info);
	
		info.setEditable(false);
		info.setBorder(blackline);
		
		JButton button0 = new JButton("Choose Directory");
		JButton button1 = new JButton("View Image");
		JButton button2 = new JButton("Add tag");
		JButton button4 = new JButton("Select tag");	
		JButton button3 = new JButton("Revert tag");
		JButton button5 = new JButton("Remove tag");
		JButton button6 = new JButton("Tags");
		JButton button7 = new JButton("History");
		JButton button8 = new JButton("Log");
			
		list.addListSelectionListener(FileChooserButtonListener.select);

		// Adding all the buttons to PanelTop
		PanelTop.add(button0);
		PanelTop.add(button1);
		PanelTop.add(button2);
		PanelTop.add(button3);
		PanelTop.add(button4);
		PanelTop.add(button5);
		PanelTop.add(button6);
		PanelTop.add(button7);
		PanelTop.add(button8);
	
		PanelLeft.setPreferredSize(new Dimension(230, 50));
		scroller.setPreferredSize(new Dimension(230, 50));
	
		add(scrollerInfo, BorderLayout.SOUTH);
		add(PanelTop, BorderLayout.PAGE_START);
		add(PanelImage1, BorderLayout.CENTER);
		add(PanelLeft, BorderLayout.LINE_START);
		add(scroller, BorderLayout.LINE_START);
			
		// Adding an action listener to each button which is handled by this class
		button0.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
	
		}	
	
	/*
	 * The ActionListener for all the buttons besides Choose Directory.
	 * The user will be prompted to enter inputs which will either be integers or strings
	 * into the console and the image can be modified. The user can also view all tags,
	 * past history of specific images and/or view s log file.
	 * 
	 * @param e 
	 * 			ActionEvent : takes an action event which is a button push 
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		
	String name = e.getActionCommand();
	BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
	Scanner reader = new Scanner(System.in);	
	
	// If the user chooses the 'Choose Directory' button, an external window will pop up
	// and prompt the user to choose a directory so that the image files can be viewed.
	if (name.equals("Choose Directory")) {
		DirectoryExplorer.buildWindow().setVisible(true);
		try {
			if (Image.saveFile.exists()) {
			Image.openFile();
			}
		} catch (IOException e1) {
			System.out.println("Directory could not be opened.");
		}
		
	}
	
	// If the user chooses the 'Add tag' button, they will be further prompted into choosing
	// how they wish to add the tag. For example, they may add a new tag, choose from pre-existing tags
	// or even revert the name of an image file back to an old one.
	else if (name.equals("Add tag")) {	
		numV = ListSelector.selectedNumber;
		if (numV > 0) {
			AddBox add = new AddBox();
			add.setVisible(true);
			}
		}
	// If the user chooses the 'Remove tag' button, then they will be given
	// the option to enter a string, and that tag will be removed from the 
	// image, if that tag exists. Otherwise, nothing happens to the image file
	else if (name.equals("Remove tag")) { 		
		numV = ListSelector.selectedNumber;
		if (numV > 0) {
			RemoveBox remove = new RemoveBox();
			remove.setVisible(true);
			}
		}
	
	else if (name.equals("Revert tag")) { 		
		numV = ListSelector.selectedNumber;
		if (numV > 0) {
			RevertBox revert= new RevertBox();
			revert.setVisible(true);
			}
		}
	
	else if (name.equals("Select tag")) { 		
		numV = ListSelector.selectedNumber;
		if (numV > 0) {
			SelectBox select = new SelectBox();
			select.setVisible(true);
			}
		} 
	
	// By pressing the 'Tags' button, the user can view all the tags
	// that are currently in use.
	else if (name.equals("Tags")) {
		info.append("There are " + Tag.count + " tags in use." + "\n");
		info.append(Tag.getNames().toString());
	}
	// If the user presses the 'History' button, then they are able to view
	// all the prior names an image has had
	else if (name.equals("History")) {
		
		int input3 = ListSelector.selectedNumber;
		if ((input3 > 0) && (input3 <= FileChooserButtonListener.numberSelector)) {
			info.append(FileChooserButtonListener.ImageListArray[input3-1].getHistory().toString() + "\n");
		}
		else {
			System.out.println("That is not a valid choice!");
		}
	}
	// If the user chooses the 'Log' button, then an external text file will pop up
	// this file contains a list of actions that have occurred while using this program.
	// It includes the (previous name : new name : date) in that format.
	else if (name.equals("Log")) {
		try {
			if (Image.logFile.exists()) {
				Image.open(Image.logFile);
				info.append("Log changes recorded in: " + Image.logFile.getAbsolutePath() + "\n");
				}
		} catch (IOException e1) {
			System.out.println("There is no log file yet.");
		}
	}
	
	else if (name.equals("View Image")) {
		repaint();
	}
	}
	// Runs the program
	public static void main (String[] args) {
		new Photorenamer().setVisible(true);
	}
		

}
