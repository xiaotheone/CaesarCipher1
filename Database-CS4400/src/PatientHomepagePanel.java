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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientHomepagePanel extends JPanel {
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public PatientHomepagePanel() throws SQLException {
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
				removeAll();
				try {
					add(new OrderMedicationPanel());
				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
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
				try {
					add(new PatientCommunicationPanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("communication page");
			}
			
		});
		
		JButton RateaDoctor = new JButton("Rate A Doctor");
		RateaDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new RateDoctorPanel());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		btnUnreadmessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new PatientMessagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		btnUnreadmessages.setBounds(321, 219, 150, 35);
//		int count = getUnreadMessage();
//		System.out.println("" + count + "unread messages");
//		btnUnreadmessages.setText("" +  count + " unread Messages");
		add(btnUnreadmessages);
		
		JLabel lblUnreadMessages = new JLabel("Unread Messages");
		lblUnreadMessages.setBounds(339, 287, 109, 16);
		int count = getUnreadMessage();
		System.out.println("" + count + "unread messages");
		btnUnreadmessages.setText("" +  count + " unread Messages");
		add(lblUnreadMessages);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public int getUnreadMessage() throws SQLException{
		String SQL = "SELECT COUNT(Status) FROM	SendsMessageToPatient As total WHERE Patusername = ?  AND Status = ? ";
		
 		int count = 0;
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			stmt.setInt(2, 0);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e){
			System.out.println("Error");
		}
		return count;
	}
	


}

