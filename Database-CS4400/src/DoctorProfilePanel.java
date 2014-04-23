import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.imageio.ImageIO;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;


/**
 * The mothod contains doctor profile panel
 * @author bochen
 *
 */
public class DoctorProfilePanel extends JPanel{
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public int edited = 0;
	private JTextField licenseField;
	private JTextField firstnameField;
	private JTextField lasenameField;
	private JTextField birthdateField;
	private JTextField workphoneField;
	private JTextField roombumberField;
	private JTextField addressField;
	JComboBox comboBoxSpeciatlty;
	JComboBox comboFrom ;
	JComboBox comboTo;
	JComboBox comboWeekday;

	
	public static BufferedImage image;

	public DoctorProfilePanel(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblLi = new JLabel("License Number:");
		lblLi.setBounds(49, 76, 113, 16);
		add(lblLi);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(49, 132, 97, 16);
		add(lblLastName);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(49, 104, 113, 16);
		add(lblFirstName);
		
		JLabel lblDate = new JLabel("Date of Birth:");
		lblDate.setBounds(49, 160, 113, 16);
		add(lblDate);
		
		JLabel lblWorkPhone = new JLabel("Work Phone:");
		lblWorkPhone.setBounds(49, 188, 113, 16);
		add(lblWorkPhone);
		
		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setBounds(49, 216, 113, 16);
		add(lblSpecialty);
		
		JLabel lblRoomNumber = new JLabel("Room Number:");
		lblRoomNumber.setBounds(49, 244, 113, 16);
		add(lblRoomNumber);
		
		JLabel lblHomeAddress = new JLabel("Home Address:");
		lblHomeAddress.setBounds(49, 272, 113, 16);
		add(lblHomeAddress);
		
		JLabel lblA = new JLabel("Avaliability:");
		lblA.setBounds(49, 300, 76, 16);
		add(lblA);
		
		JLabel lblNewLabel = new JLabel("Doctor Profile");
		lblNewLabel.setBounds(218, 24, 97, 28);
		add(lblNewLabel);
		
		licenseField = new JTextField();
		licenseField.setBounds(174, 76, 166, 16);
		add(licenseField);
		licenseField.setColumns(10);
		
		firstnameField = new JTextField();
		firstnameField.setColumns(10);
		firstnameField.setBounds(174, 104, 166, 16);
		add(firstnameField);
		
		lasenameField = new JTextField();
		lasenameField.setColumns(10);
		lasenameField.setBounds(174, 132, 166, 16);
		add(lasenameField);
		
		birthdateField = new JTextField();
		birthdateField.setColumns(10);
		birthdateField.setBounds(174, 160, 166, 16);
		add(birthdateField);
		
		workphoneField = new JTextField();
		workphoneField.setColumns(10);
		workphoneField.setBounds(174, 188, 166, 16);
		add(workphoneField);
		
		roombumberField = new JTextField();
		roombumberField.setColumns(10);
		roombumberField.setBounds(174, 244, 166, 16);
		add(roombumberField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(174, 272, 166, 16);
		add(addressField);
		
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(341, 377, 117, 29);
		add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//finish submit profile button
				try {
					createDoctorProfile();
					removeAll();
					add(new DoctorHomePage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(49, 377, 117, 29);
		add(btnGoBack);
		
		String[] speciatlty = {"General Physician", "Heart Specialist", "Eye physician", "Orthopedics", "Psychiatry", "Gynecologist"};
		
		comboBoxSpeciatlty = new JComboBox(speciatlty);
		comboBoxSpeciatlty.setBounds(174, 212, 166, 27);
		add(comboBoxSpeciatlty);
		
		JLabel lblNewLabel_1 = new JLabel("From: ");
		lblNewLabel_1.setBounds(235, 300, 40, 16);
		add(lblNewLabel_1);
		
		String[] fromTime = {"9:30","10:30", "11:30", "12:30", "1:30", "2:30", "3:30", "4:30", "5:30", "6:30"};
		comboFrom = new JComboBox(fromTime);
		comboFrom.setBounds(270, 296, 86, 27);
		add(comboFrom);
		
		JLabel lblNewLabel_2 = new JLabel("To:");
		lblNewLabel_2.setBounds(355, 300, 29, 16);
		add(lblNewLabel_2);
		
		String[] toTime = {"10:30", "11:30", "12:30", "1:30", "2:30", "3:30", "4:30", "5:30", "6:30","7:30"};
		comboTo = new JComboBox(toTime);
		comboTo.setBounds(372, 296, 86, 27);
		add(comboTo);
		
		JButton btnImport = new JButton("+");
		btnImport.setBounds(459, 295, 29, 29);
		add(btnImport);
		btnImport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//add import action
				try {
					importTime();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1);
				}
			}
		});
		
		String[] weekday = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		comboWeekday = new JComboBox(weekday);
		comboWeekday.setBounds(124, 295, 113, 27);
		add(comboWeekday);
		btnGoBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				try {
					add(new DoctorHomePage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
	}
	
	public void createDoctorProfile() throws SQLException{
		
		String SQL2 = "INSERT INTO Doctor_Availability VALUES (?,?,?,?)";
		
		if (edited == 0){
			
			String SQL = "INSERT INTO Doctor(DocUsername, LicenseNo, Fname, Lname, DOB, WorkPhone, HomeAddress, Specialty, RoomNo) VALUES (?,?,?,?,?,?,?,?,?)";
			ResultSet rs =null;
			
			try (PreparedStatement stmt = conn.prepareStatement(SQL);){
				
				System.out.println("username: " + currentDoctor.cd.getDoctorUsername());
				System.out.println("From timeL " + this.comboTo.getSelectedItem().toString());
				
				stmt.setString(1, currentDoctor.cd.getDoctorUsername());
				stmt.setInt(2, Integer.parseInt(this.licenseField.getText()));
				stmt.setString(3, this.firstnameField.getText());
				stmt.setString(4, this.lasenameField.getText());
				stmt.setString(5, this.birthdateField.getText());
				stmt.setString(6,this.workphoneField.getText());
				stmt.setString(7, this.addressField.getText());
				stmt.setString(8, this.comboBoxSpeciatlty.getSelectedItem().toString());
				stmt.setInt(9, Integer.parseInt(this.roombumberField.getText()));
				
				//check above data been update or not
				int affected = stmt.executeUpdate();
				
				
				if (affected == 1) {
					System.out.println("data successful import");
				} else {
					System.err.println("no row affected!!");
				}
				
			}
		}
		
		if(edited == 1){
			String sql = "UPDATE Doctor SET LicenseNo = ?, Fname = ?, Lname = ?, DOB = ?, WorkPhone = ?, HomeAddress = ?, Specialty = ?, RoomNo = ? WHERE DocUsername = ?";
			ResultSet rs = null;
			
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, Integer.parseInt(this.licenseField.getText()));
				stmt.setString(2, this.firstnameField.getText());
				stmt.setString(3, this.lasenameField.getText());
				stmt.setString(4, this.birthdateField.getText());
				stmt.setString(5,this.workphoneField.getText());
				stmt.setString(6, this.addressField.getText());
				stmt.setString(7, this.comboBoxSpeciatlty.getSelectedItem().toString());
				stmt.setInt(8, Integer.parseInt(this.roombumberField.getText()));
				stmt.setString(9, currentDoctor.cd.getDoctorUsername());
				
				int affected = stmt.executeUpdate();

				if (affected == 1) {
					System.out.println("data successful import");
				
				} else {
					System.err.println("no row affected!!");
				
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println(e);
			}
		}
		
		try (PreparedStatement stmt2 = conn.prepareStatement(SQL2);){
			
			stmt2.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt2.setString(2, this.comboTo.getSelectedItem().toString());
			stmt2.setString(3, this.comboFrom.getSelectedItem().toString());
			stmt2.setString(4, this.comboWeekday.getSelectedItem().toString());
			
			int affected2 = stmt2.executeUpdate();
			if (affected2 == 1) {
				System.out.println("Avaliable time imported.");
			} else {
				System.out.println("Avaliable time not imported.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}
	
	public boolean  importTime() throws SQLException{
		
		
		String SQL = "INSERT INTO Doctor_Availability  VALUES (?,?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(SQL);){
			
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt.setString(2, this.comboTo.getSelectedItem().toString());
			stmt.setString(3, this.comboFrom.getSelectedItem().toString());
			stmt.setString(4, this.comboWeekday.getSelectedItem().toString());

			int affected2 = stmt.executeUpdate();
			if (affected2 == 1) {
				System.out.println("Avaliable time imported.");
				JOptionPane.showMessageDialog(getParent(), "Time imported");
				return true;
			} else {
				System.out.println("Avaliable time not imported.");
				JOptionPane.showMessageDialog(getParent(), "Time not imported");
				return false;
			}
		} 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	
}
