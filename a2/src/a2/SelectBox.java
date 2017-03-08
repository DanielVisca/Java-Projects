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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SelectBox extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField response = new JTextField(5);
	JLabel label1 = new JLabel("TESTING");
	JTextArea display = new JTextArea(5, 5);
	Border blackline = BorderFactory.createLineBorder(Color.black);
	JScrollPane scroller = new JScrollPane(display);
	
	public SelectBox() {
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
		add(scroller, BorderLayout.CENTER);
		add(response, BorderLayout.SOUTH);
		response.addActionListener(this);
		
		display.append("Enter a number corresponding to one of the following:" + "\n" + "\n");
		
		display.append("Please select a tag by typing its number." + "\n");	
		
		int numberSelect = 0;
		//loop through tags in allTags and print the tag with its corresponding number
		Object[] tagArray = Image.tagList.toArray();
		for (int i=0; i < Image.tagList.size(); i++){
			if (tagArray[i] != null || tagArray[i].toString() != "" || tagArray[i].toString() != " ") {
				numberSelect++;
				String tag = tagArray[i].toString();
				display.append( numberSelect + ": " + tag + "\n");
			}
		}
		if (tagArray.length == 0) {
			display.append("There are no pre-existing tags!" + "\n");
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
	   
		// Gets an input from the user pertaining to the tags number
		if (Image.tagList.size() != 0) {
			String tagIndex = response.getText();
			int tagIndex2 = Integer.parseInt(tagIndex);
			
			if (((tagIndex2) >= 0)  && (tagIndex2 < Image.tagList.size())) {
				//select corresponding Tag
				Object[] tagArray = Image.tagList.toArray();
				String chosenTag = tagArray[tagIndex2].toString();
				//add the tag to the current image
				try {
					if (FileChooserButtonListener.ImageListArray[Photorenamer.numV-1].addTag(chosenTag)); {
						display.append("Tag successfully added." + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				display.append("That is not a valid option" + "\n");
				}	
		} 
	} 

}