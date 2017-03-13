package a2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Start extends JFrame implements ActionListener {
		
	/**
	 * @author Shoaib Khan
	 * 
	 * @author Daniel Visca
	 * 
	 * Initializes the PhotoRenamer program and displays the GUI, allowing the user to choose
	 * the directory, add tags, remove tags, view all tags, view the history and open up the log file.
	 */
	
	Panel PanelLeft = new Panel();
	PanelImage PanelImage1 = new PanelImage();
	static DefaultListModel dlm = new DefaultListModel();
	static JList list = new JList(dlm);
	//ListSelector select = new ListSelector(list);
	
	
	ListSelectionModel lsm = new DefaultListSelectionModel();
	
	
	JScrollPane scroller = new JScrollPane(list);
	
	private static final long serialVersionUID = 1L;
	public int len_list = 0;
	//PanelImage PanelImage1 = new PanelImage();
	
	
	public Start() {
	super("PhotoRenamer");
	
	// Initializing the parameters of the pop up window
	setSize(1200, 800);
	setResizable(false);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	//setLayout(new FlowLayout());
	getContentPane().setLayout(new GridLayout(1, 10, 50, 40));
	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//list.addListSelectionListener(FileChooserButtonListener.select);
	//list.addActionListener()
			
	// Creating the different buttons which the user can select
	
	Panel PanelTop = new Panel();
	//PanelTop.setLayout(null);
	//PanelTop.setLocation(150, 150);
	//Panel PanelLeft = new Panel();
	//PanelLeft.setLocation(100, 100);
	//PanelImage PanelImage1 = new PanelImage();
	//PanelRight.setLayout(null);
	
	JButton button0 = new JButton("Choose Directory");
	JButton button1 = new JButton("Add tag");
	JButton button2 = new JButton("Remove tag");
	JButton button3 = new JButton("Tags");
	JButton button4 = new JButton("History");
	JButton button5 = new JButton("Log");
			
	//Panel Panel = new Panel();
	
	//Panel1.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );
		
	JTextArea textArea1 = new JTextArea(47, 47);
	//GUIImageList ListOfImages = new GUIImageList();
	textArea1.setEditable(false);
			
	//JScrollPane scrollPane1 = new JScrollPane(textArea1);
	//scrollPane1.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	
	// Adding the buttons to the display
	//Panel.add();
	
	
	PanelTop.add(button0);
	PanelTop.add(button1);
	PanelTop.add(button2);
	PanelTop.add(button3);
	PanelTop.add(button4);
	PanelTop.add(button5);
	add(PanelTop);
	add(scroller);
	add(PanelLeft);
	add(PanelImage1);
	// add(PanelLeft);
	// add(PanelImage1);
	//add(Panel, BorderLayout.SOUTH);
			
	// Adding an action listener to each button which is handled by this class
	button0.addActionListener(this);
	button1.addActionListener(this);
	button2.addActionListener(this);
	button3.addActionListener(this);
	button4.addActionListener(this);
	button5.addActionListener(this);
	list.addListSelectionListener(FileChooserButtonListener.select);

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
		System.out.println("Select an image number.");
		// Verifying that the user inputs a number.
		while (!reader.hasNextInt()) {
			System.out.println("Please enter an integer.");
			reader.nextLine();
		}
		int numV = reader.nextInt();


		if ((numV > 0) && (numV <= FileChooserButtonListener.numberSelector)) {
			System.out.println("1. Add a new tag.");
			System.out.println("2. Add a pre-existing tag.");
			System.out.println("3. Revert back to a previous name.");
			while (!reader.hasNextInt()) {
				System.out.println("Please enter an integer.");
				reader.nextLine();
			}
			int num1 = reader.nextInt();
			try {
				if (num1 == 1) {
					System.out.println("Please type out a tag.");
					String newTag = reader2.readLine();
					FileChooserButtonListener.ImageListArray[numV-1].addTag(newTag);
				} else if (num1 == 2) {
					FileChooserButtonListener.ImageListArray[numV-1].tagSelection();
				} else if (num1 == 3) {
					FileChooserButtonListener.ImageListArray[numV-1].revert();
				} else {
					System.out.println("That is not a valid choice.");
				}
			} catch (IOException e1) {
				System.out.println("Please enter a valid number.");
			}	
		}
		else {
			System.out.println("That is not a valid choice!");
		}	
		}
	// If the user chooses the 'Remove tag' button, then they will be given
	// the option to enter a string, and that tag will be removed from the 
	// image, if that tag exists. Otherwise, nothing happens to the image file
	else if (name.equals("Remove tag")) { 		
		System.out.println("Select an image number.");		
		while (!reader.hasNextInt()) {
			System.out.println("Please enter an integer.");
			reader.nextLine();
		}
		int numV = reader.nextInt();
		if ((numV) > 0 && (numV) <= FileChooserButtonListener.numberSelector) {
			System.out.println("Type in the tag that you wish to remove.");
			String tagg1;
			try {
				tagg1 = reader2.readLine();
				FileChooserButtonListener.ImageListArray[numV-1].removeTag(tagg1);
			} catch (IOException e1) {
				System.out.println("That is not a valid option.");
			}
		}
		else {
			System.out.println("That is not a valid choice!");
		}
		} 
	
	// By pressing the 'Tags' button, the user can view all the tags
	// that are currently in use.
	else if (name.equals("Tags")) {
		System.out.println("There are " + Tag.count + " tags in use." );
		System.out.println(Tag.getNames());
	}
	// If the user presses the 'History' button, then they are able to view
	// all the prior names an image has had
	else if (name.equals("History")) {
		System.out.println("Select an image number.");
		while (!reader.hasNextInt()) {
			System.out.println("Please enter an integer.");
			reader.nextLine();
		}
		int input3 = reader.nextInt();
		if ((input3 > 0) && (input3 <= FileChooserButtonListener.numberSelector)) {
			System.out.println(FileChooserButtonListener.ImageListArray[input3-1].getHistory());
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
			}
		} catch (IOException e1) {
			System.out.println("There is no log file yet.");
		}
	}
	}
	// Runs the program
	public static void main (String[] args) {
		new Start().setVisible(true);
	}
		

}
