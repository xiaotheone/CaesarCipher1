import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientMakeAppointmentPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	JComboBox CbSpecialty;
	public PatientMakeAppointmentPanel() {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblScheduleAppointmentsWith = new JLabel("Schedule Appointments With Doctors");
		lblScheduleAppointmentsWith.setBounds(127, 33, 280, 16);
		add(lblScheduleAppointmentsWith);
		
		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setBounds(60, 84, 61, 16);
		add(lblSpecialty);
		
		String[] specialty = { "General Physician", "Heart Specialist", "Eye physician", "Orthopedics", "Psychiatry", "Gynecologist"};
		CbSpecialty = new JComboBox(specialty);
		CbSpecialty.setBounds(143, 80, 115, 27);
		add(CbSpecialty);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayDoctorInfo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(334, 79, 117, 29);
		add(btnSearch);
		
		JButton btnRequestAppointment = new JButton("Request Appointment");
		btnRequestAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRequestAppointment.setBounds(208, 394, 160, 29);
		add(btnRequestAppointment);
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
	
	public void displayDoctorInfo() throws SQLException{
		String SQL = "SELECT * FROM Doctor WHERE Specialty = ?";
		String DoctorName = "";
		String PhoneNumber = "";
		String RoomNumber = "";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, CbSpecialty.getSelectedItem().toString());
			rs = stmt.executeQuery();
			while(rs.next()){
				DoctorName = "DR." + rs.getString("Fname") + rs.getString("Lname");
				PhoneNumber = rs.getString("WorkPhone");
				RoomNumber = "#" +rs.getString("RoomNo");
				System.out.println(DoctorName + "     " + PhoneNumber + "      " + RoomNumber);
			}
		} 
	}
	
	
}
