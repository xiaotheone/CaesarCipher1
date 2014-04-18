import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatienProfilePanel extends JPanel{
	private JTextField nameField;
	private JTextField birthdateField;
	private JTextField addressField;
	private JTextField homePhoneField;
	private JTextField workPhoneField;
	private JTextField weightField;
	private JTextField heightField;
	private JTextField incomeField;
	private JTextField allergiesField;
	public static BufferedImage image;

	@SuppressWarnings("unchecked")
	public PatienProfilePanel() {
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		setSize(550, 450);
		setLayout(null);
		setVisible(true);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(87, 66, 97, 10);
		add(lblPatientName);
		
		JLabel lblPatientProfile = new JLabel("Patient Profile");
		lblPatientProfile.setBounds(195, 22, 105, 28);
		add(lblPatientProfile);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth:");
		lblDateOfBirth.setBounds(87, 88, 85, 16);
		add(lblDateOfBirth);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(87, 112, 61, 16);
		add(lblGender);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(87, 140, 61, 16);
		add(lblAddress);
		
		JLabel lblHomePhone = new JLabel("Home Phone:");
		lblHomePhone.setBounds(87, 168, 97, 16);
		add(lblHomePhone);
		
		JLabel lblWorkPhone = new JLabel("Work Phone:");
		lblWorkPhone.setBounds(87, 196, 85, 16);
		add(lblWorkPhone);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(87, 224, 61, 16);
		add(lblWeight);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(87, 252, 61, 16);
		add(lblHeight);
		
		JLabel lblAnnualIncome = new JLabel("Annual Income:");
		lblAnnualIncome.setBounds(87, 280, 112, 16);
		add(lblAnnualIncome);
		
		JLabel lblAllergies = new JLabel("Allergies:");
		lblAllergies.setBounds(87, 308, 85, 16);
		add(lblAllergies);
		
		nameField = new JTextField();
		nameField.setBounds(241, 57, 134, 28);
		add(nameField);
		nameField.setColumns(10);
		
		birthdateField = new JTextField();
		birthdateField.setToolTipText("format:1990-01-01");
		birthdateField.setColumns(10);
		birthdateField.setBounds(241, 82, 134, 28);
		add(birthdateField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(241, 134, 134, 28);
		add(addressField);
		
		homePhoneField = new JTextField();
		homePhoneField.setColumns(10);
		homePhoneField.setBounds(241, 162, 134, 28);
		add(homePhoneField);
		
		workPhoneField = new JTextField();
		workPhoneField.setColumns(10);
		workPhoneField.setBounds(241, 190, 134, 28);
		add(workPhoneField);
		
		weightField = new JTextField();
		weightField.setColumns(10);
		weightField.setBounds(241, 218, 134, 28);
		add(weightField);
		
		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(241, 246, 134, 28);
		add(heightField);
		
		incomeField = new JTextField();
		incomeField.setColumns(10);
		incomeField.setBounds(241, 274, 134, 28);
		add(incomeField);
		
		allergiesField = new JTextField();
		allergiesField.setColumns(10);
		allergiesField.setBounds(241, 302, 134, 28);
		add(allergiesField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(357, 342, 117, 29);
		add(btnSubmit);
		
		String[] gender = {"Male", "Female"};
		JComboBox genderComboBox = new JComboBox(gender);
		genderComboBox.setBounds(241, 108, 134, 27);
		add(genderComboBox);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}

	
