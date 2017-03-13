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
	
	public PanelImage() {
		try {
			viewImage = ImageIO.read(new File("/h/u17/c6/00/khansho1/group/group_0721/image@ok.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void imgChange(int num) {
		try {
			viewImage = ImageIO.read(FileChooserButtonListener.ImageListArray[num-1].img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(viewImage,0 ,0 , null);
	}

}
