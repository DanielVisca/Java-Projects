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
	
	public static BufferedImage viewImage;
	
	public static void imgChange(int num) {
		try {
			viewImage = ImageIO.read(new File(FileChooserButtonListener.ImageListArray[num-1].img.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setImage(String filename) {
			try {
				viewImage = ImageIO.read(new File(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	public void paintComponent(Graphics g) {
		if (viewImage != null) {
			g.drawImage(viewImage,0 ,0 , null);
		}		
	}
	

}
