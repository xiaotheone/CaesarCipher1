import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPatientHomepage = new JLabel("Patient Homepage");
		lblPatientHomepage.setBounds(214, 25, 136, 16);
		add(lblPatientHomepage);
		
		JButton btnMakeAppointment = new JButton("Make Appointment");
		btnMakeAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnMakeAppointment.setBounds(74, 103, 150, 35);
		add(btnMakeAppointment);
		
		JButton btnViewVisitHistory = new JButton("View Visit History");
		btnViewVisitHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnViewVisitHistory.setBounds(74, 163, 150, 35);
		add(btnViewVisitHistory);
		
		JButton btnOrderMedication = new JButton("Order Medication");
		btnOrderMedication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOrderMedication.setBounds(74, 219, 150, 35);
		add(btnOrderMedication);
		
		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.setBounds(74, 279, 150, 35);
		add(btnCommunicate);
		btnCommunicate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new PatientCommunicationPanel());
				System.out.println("communication page");
			}
			
		});
		
		JButton RateaDoctor = new JButton("Rate A Doctor");
		RateaDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		RateaDoctor.setBounds(321, 103, 150, 35);
		add(RateaDoctor);
		
		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.setBounds(321, 163, 150, 35);
		add(btnEditProfile);
		btnEditProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				add(new PatientProfilePanel());
				repaint();

			}
			
		});
		
		JButton btnUnreadmessages = new JButton("Unread Messages");
		btnUnreadmessages.setBounds(321, 219, 150, 35);
		add(btnUnreadmessages);
		
		
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
