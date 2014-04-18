import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientHomepagePanel extends JPanel{
	
	
	public static BufferedImage image;
	public PatientHomepagePanel() {
		setLayout(null);
		
		JLabel lblPatientHomepage = new JLabel("Patient Homepage");
		lblPatientHomepage.setBounds(137, 27, 136, 16);
		add(lblPatientHomepage);
		
		JLabel lblEditProfile = new JLabel("Edit Profile");
		lblEditProfile.setBounds(54, 114, 85, 16);
		add(lblEditProfile);
		lblEditProfile.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e){
				removeAll();
				add(new PatienProfilePanel());
				repaint();
			}
	
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
