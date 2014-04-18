import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


/**
 * The mothod contains doctor profile panel
 * @author bochen
 *
 */
public class DoctorProfilePanel extends JPanel{
	private JTextField licenseField;
	private JTextField firstnameField;
	private JTextField lasenameField;
	private JTextField birthdateField;
	private JTextField workphoneField;
	private JTextField specialtyField;
	private JTextField roombumberField;
	private JTextField addressField;
	private JTextField avaliabilityField_8;
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
		
		JLabel lblA = new JLabel("Avaliability");
		lblA.setBounds(49, 300, 113, 16);
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
		
		specialtyField = new JTextField();
		specialtyField.setColumns(10);
		specialtyField.setBounds(174, 216, 166, 16);
		add(specialtyField);
		
		roombumberField = new JTextField();
		roombumberField.setColumns(10);
		roombumberField.setBounds(174, 244, 166, 16);
		add(roombumberField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(174, 272, 166, 16);
		add(addressField);
		
		avaliabilityField_8 = new JTextField();
		avaliabilityField_8.setColumns(10);
		avaliabilityField_8.setBounds(174, 300, 166, 16);
		add(avaliabilityField_8);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(341, 377, 117, 29);
		add(btnNewButton);
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(49, 377, 117, 29);
		add(btnGoBack);
		btnGoBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				add(new LoginPanel());
				
			}
		});
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	
}
