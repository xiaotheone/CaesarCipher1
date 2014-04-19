import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class RateDoctorPanel extends JPanel{
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	public RateDoctorPanel() {
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblRateADoctor = new JLabel("Rate A Doctor");
		lblRateADoctor.setBounds(215, 23, 126, 23);
		add(lblRateADoctor);
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
