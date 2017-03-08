package a2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelImage extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Stores the image of the file
	public static BufferedImage viewImage;
	
	/**
	 * Changes the viewImage to another from the ImageListArray
	 * 
	 *
	 * @param num
	 *            an integer value contained within the index of ImageListArray
	 */
	public static void imgChange(int num) {
		try {
			viewImage = ImageIO.read(new File(FileChooserButtonListener.ImageListArray[num-1].img.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Similar to imgChange, but can set the image using a filename string instead
	 * 
	 *
	 * @param filename
	 *            a string representation of a file
	 */
	public static void setImage(String filename) {
			try {
				viewImage = ImageIO.read(new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	/**
	 * Draws the image onto the panel
	 * 
	 *
	 * @param g
	 *            the graphic image to be painted
	 */
	public void paintComponent(Graphics g) {
		if (viewImage != null) {
			g.drawImage(viewImage,0 ,0 , null);
		}		
	}
	

}
