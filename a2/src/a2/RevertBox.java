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

public class RevertBox extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField response = new JTextField(5);
	JLabel label1 = new JLabel("TESTING");
	JTextArea display = new JTextArea(5, 5);
	Border blackline = BorderFactory.createLineBorder(Color.black);
	
	public RevertBox() {
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
		
		display.append("Choose a number from the list of previous names." + "\n" + "\n");
		
		// Displaying the list of previous names
		String[] listNames = FileChooserButtonListener.ImageListArray[Photorenamer.numV].history.toString().split("\n");
		int counterV = 1;
		for (String items: listNames) {
			display.append(counterV + ". " + items + "\n");
			counterV++;
		}
	}
	
	
	/**
	 * Performs an action when the user inputs enter
	 * 
	 *
	 * @param evt
	 *            when the user inputs enter
	
	 */
	public void actionPerformed(ActionEvent evt) {
	   
		// Getting an input from the user
		String newTag = response.getText();
		int num1 = Integer.parseInt(newTag);
		
		// Using the revert method to change the image name to a previous one
		if (FileChooserButtonListener.ImageListArray[Photorenamer.numV-1].revert(num1)) {
			display.append("Tag name successfully reverted." + "\n");
		} else {
			display.append("Tag name could not be reverted." + "\n");
			}
		} 
}