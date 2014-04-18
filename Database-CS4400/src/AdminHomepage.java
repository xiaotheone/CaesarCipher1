import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The class contains the home page of administrative
 * @author bochen
 *
 */
public class AdminHomepage extends JPanel{
	
	public static BufferedImage image;


	public AdminHomepage(){		
		
		setSize(550, 450);
		setLayout(null);
		
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
