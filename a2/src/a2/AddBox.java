package a2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AddBox extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField response = new JTextField(5);
	JLabel label1 = new JLabel("TESTING");
	JTextArea display = new JTextArea(5, 5);
	Border blackline = BorderFactory.createLineBorder(Color.black);
	
	
	public AddBox() {
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
		
		display.append("Please enter a tag." + "\n");
	}
	
	/**
	 * Performs an action when the user inputs enter
	 * 
	 *
	 * @param evt
	 *            when the user inputs enter
	
	 */
	public void actionPerformed(ActionEvent evt) {
	   
		// Gets input from the user, the name of the tag they want to add
		String newTag = response.getText();
		try {
			// calls the addTag method
			if (FileChooserButtonListener.ImageListArray[Photorenamer.numV-1].addTag(newTag)) {
					display.append("Tag succesfully added." + "\n");
				} else {
					display.append("This image already contains this tag!" + "\n");
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setText("");
		} 

}
