import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;



/**
 * The method contains patient profile panel,
 * it lets user input patient information
 * @author hailin
 *
 */
public class PatientProfilePanel extends JPanel{
	

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	private JTextField nameField;
	private JTextField birthdateField;
	private JTextField addressField;
	private JTextField homePhoneField;
	private JTextField workPhoneField;
	private JTextField weightField;
	private JTextField heightField;
	private JTextField incomeField;
	private JTextField allergiesField;
	protected String patientUsername;
	public static BufferedImage image;
	private JComboBox genderComboBox;
	private JTextField emergencynameTextFiled;
	private JTextField emergencyPhoneTextFiled;

	@SuppressWarnings("unchecked")
	public PatientProfilePanel() {
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		setSize(650, 550);
		setLayout(null);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(92, 72, 97, 10);
		add(lblPatientName);
		
		JLabel lblPatientProfile = new JLabel("Patient Profile");
		lblPatientProfile.setBounds(190, 24, 105, 28);
		add(lblPatientProfile);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth:");
		lblDateOfBirth.setBounds(92, 94, 85, 16);
		add(lblDateOfBirth);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(92, 129, 61, 16);
		add(lblGender);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(92, 159, 61, 16);
		add(lblAddress);
		
		JLabel lblHomePhone = new JLabel("Home Phone:");
		lblHomePhone.setBounds(92, 191, 97, 16);
		add(lblHomePhone);
		
		JLabel lblWorkPhone = new JLabel("Work Phone:");
		lblWorkPhone.setBounds(92, 228, 85, 16);
		add(lblWorkPhone);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(92, 256, 61, 16);
		add(lblWeight);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(92, 286, 61, 16);
		add(lblHeight);
		
		JLabel lblAnnualIncome = new JLabel("Annual Income:");
		lblAnnualIncome.setBounds(92, 319, 112, 16);
		add(lblAnnualIncome);
		
		JLabel lblAllergies = new JLabel("Allergies:");
		lblAllergies.setBounds(92, 351, 85, 16);
		add(lblAllergies);
		
		nameField = new JTextField();
		nameField.setBounds(246, 63, 134, 28);
		add(nameField);
		nameField.setColumns(10);
		
		birthdateField = new JTextField();
		birthdateField.setToolTipText("format:1990-01-01");
		birthdateField.setColumns(10);
		birthdateField.setBounds(246, 94, 134, 28);
		add(birthdateField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(246, 153, 134, 28);
		add(addressField);
		
		homePhoneField = new JTextField();
		homePhoneField.setColumns(10);
		homePhoneField.setBounds(246, 185, 134, 28);
		add(homePhoneField);
		
		workPhoneField = new JTextField();
		workPhoneField.setColumns(10);
		workPhoneField.setBounds(246, 219, 134, 28);
		add(workPhoneField);
		
		weightField = new JTextField();
		weightField.setColumns(10);
		weightField.setBounds(246, 250, 134, 28);
		add(weightField);
		
		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(246, 280, 134, 28);
		add(heightField);
		
		incomeField = new JTextField();
		incomeField.setColumns(10);
		incomeField.setBounds(246, 313, 134, 28);
		add(incomeField);
		
		allergiesField = new JTextField();
		allergiesField.setColumns(10);
		allergiesField.setBounds(246, 345, 134, 28);
		add(allergiesField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(398, 439, 117, 29);
		add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					createPatientProfile();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		String[] gender = {"Male", "Female"};
	    genderComboBox = new JComboBox(gender);
		genderComboBox.setBounds(246, 125, 134, 27);
		add(genderComboBox);
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(30, 439, 117, 29);
		add(btnGoBack);
		
		JLabel lblEmergencyContactName = new JLabel("Emergency Contact Name:");
		lblEmergencyContactName.setBounds(92, 378, 144, 14);
		add(lblEmergencyContactName);
		
		emergencynameTextFiled = new JTextField();
		emergencynameTextFiled.setBounds(246, 375, 134, 20);
		add(emergencynameTextFiled);
		emergencynameTextFiled.setColumns(10);
		
		JLabel lblEmergencyContactPhone = new JLabel("Emergency Contact phone:");
		lblEmergencyContactPhone.setBounds(92, 403, 154, 14);
		add(lblEmergencyContactPhone);
		
		emergencyPhoneTextFiled = new JTextField();
		emergencyPhoneTextFiled.setBounds(246, 400, 134, 20);
		add(emergencyPhoneTextFiled);
		emergencyPhoneTextFiled.setColumns(10);
		
		JButton button = new JButton("+ ");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CreateAllergy();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(400, 348, 66, 23);
		add(button);
		btnGoBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				try {
					add(new PatientHomepagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	public boolean CreateAllergy() throws SQLException{
		String SQL ="INSERT INTO Patient_Allergies VALUES (?, ?)";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
		
		
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			stmt.setString(2, this.allergiesField.getText());
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				System.out.println("allergy imported");
				JOptionPane.showMessageDialog(getParent(), "allergy imported");
				return true;
			
			} else {
				System.err.println("allergy importing failed");
				return false;
			}
		}

	}
	public boolean createPatientProfile() throws SQLException{
		String SQL="INSERT INTO Patient(PatientUsername, Name, DOB, Gender, Address, WorkPhone, HomePhone, EmerContactName, EmerContactPhone, Weight, Height, AnnualIncome) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		ResultSet rs =null;
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1,currentPatient.cp.getPatientUsername());
			stmt.setString(2,this.nameField.getText());
			stmt.setString(3,this.birthdateField.getText());
			stmt.setString(4,this.genderComboBox.getSelectedItem().toString());
			stmt.setString(5, this.addressField.getText());
			stmt.setInt(6,Integer.parseInt(this.workPhoneField.getText()));
			stmt.setInt(7,Integer.parseInt(this.homePhoneField.getText()));
			stmt.setString(8,this.emergencynameTextFiled.getText());		
			stmt.setString(9,this.emergencyPhoneTextFiled.getText());		
			stmt.setInt(10,Integer.parseInt(this.weightField.getText()));		
			stmt.setInt(11,Integer.parseInt(this.heightField.getText()));		
			stmt.setString(12,this.incomeField.getText());		
			//stmt.setInt(13,0);
			
			int affected = stmt.executeUpdate();
			String SQL2 ="INSERT INTO Patient_Allergies VALUES (?, ?)";
			
			try(PreparedStatement stmt2 = conn.prepareStatement(SQL2);) {

				stmt2.setString(1, currentPatient.cp.getPatientUsername());
				stmt2.setString(2, this.allergiesField.getText());
				int affected2 = stmt2.executeUpdate();
				if (affected2 == 1) {
					System.out.println("allergy imported");
					
				} else {
					System.err.println("allergy importing failed");
					
				}
			}
			if (affected == 1) {
				System.out.println("data successful import");
				return true;
			} else {
				System.err.println("no row affected!!");
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

	
