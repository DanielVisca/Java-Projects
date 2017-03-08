package a2;
//This class has a lazy implementation of Singleton
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class RemoveBox extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JTextField response = new JTextField("TYPE HERE", 5);
	JLabel label1 = new JLabel("TESTING");
	JTextArea display = new JTextArea(5, 5);
	Border blackline = BorderFactory.createLineBorder(Color.black);
	
	public RemoveBox() {
		super("User input");
		
		// Initializing the parameters of the pop up window
		setSize(350, 350);
		setResizable(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);

		
		// Setting borders and other properties to the fields
		response.setBorder(blackline);
		display.setBorder(blackline);
		display.setEditable(false);
		
		// adding the fields onto the main frame
		add(label1, BorderLayout.PAGE_START);
		add(display, BorderLayout.CENTER);
		add(response, BorderLayout.SOUTH);
		response.addActionListener(this);
		
		display.append("Type in the tag that you wish to remove." + "\n" + "Please do not include the @." + "\n");
		display.append("Currently selected image number: " + ListSelector.selectedNumber + "\n");
	}
	
	/**
	 * Performs an action when the user inputs enter
	 * 
	 *
	 * @param evt
	 *            when the user inputs enter
	
	 */
	public void actionPerformed(ActionEvent evt) {
		// Gets an input from the user on which tag to remove
	    String text = response.getText();
	    display.append(text + "\n");
		try {
			// Calls the removeTag method to perform the operation
			if (FileChooserButtonListener.ImageListArray[Photorenamer.numV-1].removeTag(text)) {
				display.append("Tag successfully removed." + "\n");
			} else {
				display.append("Could not remove tag!" + "\n");
			}
		} catch (IOException e1) {
			System.out.println("That is not a valid option.");
		}
	}

	//Lazy initialization of Singleton. I don't mean this as in I am being lazy it is the actual design pattern
	public static JTextField getInstance(){
		if (response == null){
			response = new JTextField();	
		}
		return response;
	}

}