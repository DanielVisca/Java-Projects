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
	
	public void valueChanged(ListSelectionEvent e) {
		
        boolean adjust = e.getValueIsAdjusting();
    	int point = list1.getSelectedIndex();
    	
	    if (adjust == false) {
	    	if (point >= 0 && point <= list1.getModel().getSize()) {
	    		PanelImage.imgChange(point+1);
	    		selectedNumber = point + 1;
    		}
	    } 
	} 
}
	