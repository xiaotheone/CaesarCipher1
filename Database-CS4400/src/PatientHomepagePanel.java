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
import javax.swing.JTextField;

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
		
		JButton btnMakeAppointment = new JButton("Make Appointment");
		btnMakeAppointment.setBounds(26, 67, 150, 35);
		add(btnMakeAppointment);
		
		JButton btnViewVisitHistory = new JButton("View Visit History");
		btnViewVisitHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnViewVisitHistory.setBounds(26, 114, 150, 35);
		add(btnViewVisitHistory);
		
		JButton btnOrderMedication = new JButton("Order Medication");
		btnOrderMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOrderMedication.setBounds(26, 161, 150, 35);
		add(btnOrderMedication);
		
		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.setBounds(26, 207, 150, 35);
		add(btnCommunicate);
		
		JButton RateaDoctor = new JButton("Rate A Doctor");
		RateaDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		RateaDoctor.setBounds(236, 70, 150, 35);
		add(RateaDoctor);
		
		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.setBounds(236, 114, 150, 35);
		add(btnEditProfile);
		btnEditProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeAll();
				add(new PatienProfilePanel());
				repaint();
			}
			
		});
		
		JButton btnUnreadmessages = new JButton("UnreadMessages");
		btnUnreadmessages.setBounds(236, 161, 150, 35);
		add(btnUnreadmessages);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
