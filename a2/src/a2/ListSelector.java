package a2;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.lang.Object;

public class ListSelector implements ListSelectionListener {
	
	public static JList list1;
	public static int selectedNumber;
	
	
	public static void change(JList list) {
		list1 = list;
	}
	
	/**
	 * Performs an action when the user selects an item
	 * 
	 *
	 * @param e
	 *            when the user selects one of the items from the list
	 */
	public void valueChanged(ListSelectionEvent e) {
		
		// Gets the selected value of the item from the JList
        boolean adjust = e.getValueIsAdjusting();
    	int point = list1.getSelectedIndex();
    	
    	// Changes the image depending on which item is selected (when view image is clicked)
	    if (adjust == false) {
	    	if (point >= 0 && point <= list1.getModel().getSize()) {
	    		PanelImage.imgChange(point+1);
	    		selectedNumber = point + 1;
    		}
	    } 
	} 
}
	