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

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatienProfilePanel extends JPanel{
	private JTextField textField;
		
	public PatienProfilePanel() {
		setLayout(null);
		
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
		
		textField = new JTextField();
		textField.setBounds(241, 57, 134, 28);
		add(textField);
		textField.setColumns(10);

	}
}
