import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * @author hailin
 *
 */
public class DoctorCommunicationPanel extends JPanel{
	private JComboBox doctorNameComboBox;
	private JComboBox PatientNameComboBox;
	private JTextField textField;
	private JTextField textField_1;
	private static Connection conn = ConnectionManager.getInstance().getConnection();

	public static BufferedImage image;
	public DoctorCommunicationPanel() throws SQLException {
		setLayout(null);
		setSize(550, 450);
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		JLabel lblSelectDoctor = new JLabel("Select Doctor");
		lblSelectDoctor.setBounds(10, 83, 94, 16);
		add(lblSelectDoctor);
		String[] docNames = doctorList();
		 doctorNameComboBox = new JComboBox(docNames);
		 doctorNameComboBox.setBounds(93, 79, 100, 27);
		add(doctorNameComboBox);
	
		JLabel lblSelectPatient = new JLabel("Select Patient");
		lblSelectPatient.setBounds(241, 83, 91, 16);
		add(lblSelectPatient);
		
		String[] patNames = patientList();
	    PatientNameComboBox = new JComboBox(patNames);
	    PatientNameComboBox.setBounds(324, 79, 111, 27);
		add(PatientNameComboBox);
		
		textField = new JTextField();
		textField.setBounds(20, 151, 181, 123);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, 123, 61, 16);
		add(lblMessage);
		
		JLabel lblMessage_1 = new JLabel("Message:");
		lblMessage_1.setBounds(241, 123, 61, 16);
		add(lblMessage_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(251, 151, 189, 123);
		add(textField_1);
		
		JButton btnSendMessage = new JButton("Send Message to Doc");
		btnSendMessage.setBounds(20, 299, 189, 29);
		add(btnSendMessage);
		
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendMessageToDoc();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnSendToPat = new JButton("Send Message to Patient");
		btnSendToPat.setBounds(268, 302, 167, 26);
		add(btnSendToPat);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				try {
					add(new DoctorHomePage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		lblBack.setBounds(25, 369, 61, 27);
		add(lblBack);
		btnSendToPat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendMessageToPat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
public String[] doctorList() throws SQLException{
		
		String SQL = "SELECT Lname FROM Doctor WHERE DocUsername != \""+ currentDoctor.cd.getDoctorUsername() + "\"";
		
		Statement stmt = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(SQL);
		List<String> nameList = new ArrayList<String>();
		while(rs.next()) {
			nameList.add("DR." + rs.getString("Lname"));
			//nameList.add( rs.getString("Lname"));
			
			
		}
		
		String[] docNames = (String[]) nameList.toArray(new String[nameList.size()]);
		for(int i = 0; i < docNames.length; i++){
			System.out.println(docNames[i]);
		}
		return docNames;
		
	}
public String[] patientList() throws SQLException{
	
	String SQL = "SELECT PatientUsername, Name FROM Patient";
	
	Statement stmt = conn.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	ResultSet rs = stmt.executeQuery(SQL);
	List<String> nameList = new ArrayList<String>();
	while(rs.next()) {
		nameList.add(rs.getString("Name"));
		//nameList.add( rs.getString("Lname"));
		
		
	}
	
	String[] patNames = (String[]) nameList.toArray(new String[nameList.size()]);
		for (int i = 0; i < patNames.length; i++) {
			System.out.println(patNames[i]);
		}
		return patNames;

	}

	public String getDoctorUsename(String Lname) throws SQLException {

		String SQL = "SELECT DocUsername FROM Doctor WHERE Lname = ?";
		String s = "";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, Lname);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			s = rs.getString("DocUsername");
		}

		return s;

	}

	public String getPatientUsename(String name) throws SQLException {

		String SQL = "SELECT PatientUsername FROM Patient WHERE name = ?";
		String s = "";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			s = rs.getString("PatientUsername");
		}

		return s;

	}
	
	public boolean sendMessageToDoc() throws SQLException{
		String SQL = "INSERT INTO CommunicatesWith VALUES (?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			
			//System.out.println("cp "+ currentPatient.cp.getPatientUsername() );
			stmt.setString(2, getDoctorUsename((doctorNameComboBox.getSelectedItem()).toString().substring(3)));
			stmt.setString(3, getCurrentTimeStamp());
			stmt.setString(4, textField.getText() );
			stmt.setInt(5, 0);
			
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				System.out.println("message sent");
				JOptionPane.showMessageDialog(getParent(), "message sent");

				return true;
			} else {
				System.err.println("error");
				JOptionPane.showMessageDialog(getParent(), "error");
				return false;
			}
		}
		
	
	}
	public boolean sendMessageToPat() throws SQLException{
		String SQL = "INSERT INTO SendsMessageToPatient VALUES (?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			
			//System.out.println("cp "+ currentPatient.cp.getPatientUsername() );
			stmt.setString(2, getPatientUsename(PatientNameComboBox.getSelectedItem().toString()));
			stmt.setString(3, getCurrentTimeStamp());
			stmt.setString(4, textField_1.getText() );
			stmt.setInt(5, 0);
			
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				System.out.println("message sent");
				JOptionPane.showMessageDialog(getParent(), "message sent");

				return true;
			} else {
				System.err.println("error");
				JOptionPane.showMessageDialog(getParent(), "error");
				return false;
			}
		}
		
	
	}
	private static String getCurrentTimeStamp() {
		 
		java.util.Date today = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(today.getTime());
 
	}
}
