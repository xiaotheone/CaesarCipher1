import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
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
 * The mothod contains the home page of doctor
 * including all the function doctor can use
 * @author bochen
 *
 */
public class DoctorHomePage extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();

	
	public DoctorHomePage() throws SQLException{
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Homepage for Doctor");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(176, 30, 173, 34);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("View Appointment");
		btnNewButton.setBounds(58, 86, 130, 45);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeAll();
				add(new ViewAppointmentPanel());
			}
		});
		
		JButton btnPatientVisit = new JButton("Patient Visit History");
		btnPatientVisit.setBounds(58, 148, 130, 45);
		add(btnPatientVisit);
		btnPatientVisit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeAll();
				add(new PatientVisitPanel());
			}
		});
		
		JButton btnRecord = new JButton("Record a surgery");
		btnRecord.setBounds(58, 205, 130, 45);
		add(btnRecord);
		
		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new DoctorCommunicationPanel());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCommunicate.setBounds(58, 259, 130, 45);
		add(btnCommunicate);
		
		JButton btnEditeProfile = new JButton("Edite Profile");
		btnEditeProfile.setBounds(58, 316, 130, 44);
		add(btnEditeProfile);
		btnEditeProfile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				try {
					add(new DoctorProfilePanel());
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(377, 373, 117, 29);
		add(btnClose);
		
		JButton btnMessage = new JButton("Unread Message");
		btnMessage.setBounds(257, 86, 130, 45);
		add(btnMessage);
		btnMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new doctorMessagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		int count = getUnreadMessage();
		System.out.println("" + count + "unread messages");
		btnMessage.setText("" +  count + " unread Messages");

		
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
	public int getUnreadMessage() throws SQLException{
		String SQL = "SELECT COUNT(Status) FROM	Sends_messageToDoc As total WHERE DocUsername = ?  AND Status = ? ";
		
 		int count = 0;
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt.setInt(2, 0);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			
			
			
		} catch (SQLException e){
			System.out.println("Error");
		}
		String SQL2 = "SELECT COUNT(Status) FROM	CommunicatesWith As total WHERE Doc_Receiver = ?  AND Status = ? ";
		try(PreparedStatement stmt = conn.prepareStatement(SQL2);){
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt.setInt(2, 0);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				count += rs.getInt(1);
			}
			
			
			
		} catch (SQLException e){
			System.out.println("Error");
		}
		
		return count;
	}
	
}
