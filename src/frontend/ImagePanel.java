package frontend;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel 
{
	Image image;
	
	public ImagePanel(Image i)
	{
		image = i;
	}
	
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
		g.drawImage(image,0,0,null);
	}
}
