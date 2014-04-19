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
	public static BufferedImage image;

	@SuppressWarnings("unchecked")
	public PatientProfilePanel() {
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(87, 81, 97, 10);
		add(lblPatientName);
		
		JLabel lblPatientProfile = new JLabel("Patient Profile");
		lblPatientProfile.setBounds(195, 22, 105, 28);
		add(lblPatientProfile);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth:");
		lblDateOfBirth.setBounds(87, 103, 85, 16);
		add(lblDateOfBirth);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(87, 138, 61, 16);
		add(lblGender);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(87, 168, 61, 16);
		add(lblAddress);
		
		JLabel lblHomePhone = new JLabel("Home Phone:");
		lblHomePhone.setBounds(87, 200, 97, 16);
		add(lblHomePhone);
		
		JLabel lblWorkPhone = new JLabel("Work Phone:");
		lblWorkPhone.setBounds(87, 237, 85, 16);
		add(lblWorkPhone);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(87, 265, 61, 16);
		add(lblWeight);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(87, 295, 61, 16);
		add(lblHeight);
		
		JLabel lblAnnualIncome = new JLabel("Annual Income:");
		lblAnnualIncome.setBounds(87, 328, 112, 16);
		add(lblAnnualIncome);
		
		JLabel lblAllergies = new JLabel("Allergies:");
		lblAllergies.setBounds(87, 360, 85, 16);
		add(lblAllergies);
		
		nameField = new JTextField();
		nameField.setBounds(241, 72, 134, 28);
		add(nameField);
		nameField.setColumns(10);
		
		birthdateField = new JTextField();
		birthdateField.setToolTipText("format:1990-01-01");
		birthdateField.setColumns(10);
		birthdateField.setBounds(241, 103, 134, 28);
		add(birthdateField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(241, 162, 134, 28);
		add(addressField);
		
		homePhoneField = new JTextField();
		homePhoneField.setColumns(10);
		homePhoneField.setBounds(241, 194, 134, 28);
		add(homePhoneField);
		
		workPhoneField = new JTextField();
		workPhoneField.setColumns(10);
		workPhoneField.setBounds(241, 228, 134, 28);
		add(workPhoneField);
		
		weightField = new JTextField();
		weightField.setColumns(10);
		weightField.setBounds(241, 259, 134, 28);
		add(weightField);
		
		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(241, 289, 134, 28);
		add(heightField);
		
		incomeField = new JTextField();
		incomeField.setColumns(10);
		incomeField.setBounds(241, 322, 134, 28);
		add(incomeField);
		
		allergiesField = new JTextField();
		allergiesField.setColumns(10);
		allergiesField.setBounds(241, 354, 134, 28);
		add(allergiesField);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(383, 393, 117, 29);
		add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		String[] gender = {"Male", "Female"};
		JComboBox genderComboBox = new JComboBox(gender);
		genderComboBox.setBounds(241, 134, 134, 27);
		add(genderComboBox);
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(31, 393, 117, 29);
		add(btnGoBack);
		btnGoBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				add(new LoginPanel());
				
			}
		});

	}

	public void createPatientProfile() throws SQLException{
		String SQL="INSERT INTO Patient VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ResultSet rs =null;
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
		
		}
			
		}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}

	
