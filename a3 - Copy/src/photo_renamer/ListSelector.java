package a2;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.Scrollable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListSelector implements ListSelectionListener  {
	
	public static JList list1;
	
	//public ListSelector(JList list) { 
		//
	//}
	
	public static void change(JList list) {
		list1 = list;
	}
	
	public void valueChanged(ListSelectionEvent e) {
        boolean adjust = e.getValueIsAdjusting();
	    if (adjust == false) {
	    	//System.out.println("Choose something");
	     if (list1.getSelectedIndex() == 0){
	    	 list1.setSelectedIndex(10);
		    System.out.println("1st image");
	    } else if (list1.getSelectedIndex() == 1){
	    	 list1.setSelectedIndex(10);
		    System.out.println("2nd image");
	    } else if (list1.getSelectedIndex() == 2){
	    	 list1.setSelectedIndex(10);
			System.out.println("3rd image");
	    } else if (list1.getSelectedIndex() == 3){
	    	 list1.setSelectedIndex(10);
			System.out.println("4th image");
		}
	    }
	} 
}
	