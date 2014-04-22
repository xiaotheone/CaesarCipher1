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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JComboBox;


public class RecordSurgeryPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField inputPatientField;
	private JTextField nametextField;
	private JTextField surgerytextField;
	private JTextField CPTField;
	private JTextField anesthesiaField;
	private JTextField surgeryStartField;
	private JTextField surgeryCompleteField;
	private JComboBox comboPatient;
	private String patientString, patientName, patientPhone;
	
	public RecordSurgeryPanel(){
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblSurgeryRecord = new JLabel("Surgery Record");
		lblSurgeryRecord.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblSurgeryRecord.setBounds(207, 32, 114, 29);
		add(lblSurgeryRecord);
		
		JLabel lblNewLabel = new JLabel("Search Patient:");
		lblNewLabel.setBounds(69, 75, 92, 16);
		add(lblNewLabel);
		
		
		inputPatientField = new JTextField();
		inputPatientField.setBounds(170, 69, 134, 28);
		add(inputPatientField);
		inputPatientField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(314, 70, 117, 29);
		add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					System.out.println("searching patient");
					searchPatient();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1);
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Patient Name");
		lblNewLabel_1.setBounds(10, 195, 83, 16);
		add(lblNewLabel_1);
		
		JLabel lblSurgeryName = new JLabel("Surgeon Name");
		lblSurgeryName.setBounds(10, 223, 92, 16);
		add(lblSurgeryName);
		
		JLabel lblProcedureName = new JLabel("Procedure Name");
		lblProcedureName.setBounds(10, 251, 107, 16);
		add(lblProcedureName);
		
		JLabel lblCptCode = new JLabel("CPT Code");
		lblCptCode.setBounds(10, 279, 61, 16);
		add(lblCptCode);
		
		JLabel lblNewLabel_2 = new JLabel("Number of Assistant");
		lblNewLabel_2.setBounds(10, 307, 107, 16);
		add(lblNewLabel_2);
		
		JLabel lblPreoperative = new JLabel("Pre-operative Medication");
		lblPreoperative.setBounds(6, 347, 96, 16);
		add(lblPreoperative);
		
		JTextArea operativeArea = new JTextArea();
		operativeArea.setBounds(124, 347, 104, 38);
		add(operativeArea);
		
		nametextField = new JTextField();
		nametextField.setEditable(false);
		nametextField.setBounds(114, 189, 114, 28);
		add(nametextField);
		nametextField.setColumns(10);
		
		surgerytextField = new JTextField();
		surgerytextField.setEditable(false);
		surgerytextField.setColumns(10);
		surgerytextField.setBounds(114, 217, 114, 28);
		add(surgerytextField);
		
		CPTField = new JTextField();
		CPTField.setColumns(10);
		CPTField.setBounds(114, 273, 114, 28);
		add(CPTField);
		
		String[] precedure = {"General Physician", "Heart Specialist", "Eye physician", "Orthopedics", "Psychiatry", "Gynecologist"};

		JComboBox comboProcedure = new JComboBox(precedure);
		comboProcedure.setBounds(114, 247, 114, 27);
		add(comboProcedure);
		
		JComboBox comboNoAss = new JComboBox();
		comboNoAss.setBounds(114, 303, 114, 27);
		add(comboNoAss);
		
		JLabel lblNewLabel_3 = new JLabel("Anesthesia Start Time");
		lblNewLabel_3.setBounds(256, 195, 122, 16);
		add(lblNewLabel_3);
		
		anesthesiaField = new JTextField();
		anesthesiaField.setBounds(390, 189, 83, 28);
		add(anesthesiaField);
		anesthesiaField.setColumns(10);
		
		JLabel lblSurgeryStartTime = new JLabel("Surgery Start time");
		lblSurgeryStartTime.setBounds(256, 223, 122, 16);
		add(lblSurgeryStartTime);
		
		surgeryStartField = new JTextField();
		surgeryStartField.setColumns(10);
		surgeryStartField.setBounds(390, 217, 83, 28);
		add(surgeryStartField);
		
		JLabel lblSurgeryCompleteTime = new JLabel("Surgery Complete time");
		lblSurgeryCompleteTime.setBounds(256, 251, 122, 16);
		add(lblSurgeryCompleteTime);
		
		surgeryCompleteField = new JTextField();
		surgeryCompleteField.setColumns(10);
		surgeryCompleteField.setBounds(390, 245, 83, 28);
		add(surgeryCompleteField);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(256, 307, 109, 16);
		add(lblDescription);
		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setBounds(377, 307, 136, 82);
		add(descriptionArea);
		
		JButton btnRecord = new JButton("Record");
		btnRecord.setBounds(204, 398, 117, 29);
		add(btnRecord);
		
		JLabel lblPatientName = new JLabel("Patient Name      Phone Number");
		lblPatientName.setBounds(10, 103, 200, 21);
		add(lblPatientName);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(228, 125, 117, 29);
		add(btnSelect);
		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				patientString = comboPatient.getSelectedItem().toString();
				System.out.println(patientString);
				patientName = patientString.split(" ")[0] + " " +patientString.split(" ")[1];
				patientPhone = patientString.split("    ")[1];
				System.out.println(patientName + " " + patientPhone);
				nametextField.setText(patientName);
			}
		});
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(29, 403, 95, 29);
		add(btnGoBack);
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeAll();
				try {
					add(new DoctorHomePage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		comboPatient = new JComboBox();
		comboPatient.setBounds(10, 126, 218, 27);
		add(comboPatient);
		
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchPatient() throws SQLException{
		
		String SQL = "SELECT Name, HomePhone FROM Patient WHERE	Name = ?";
		String SQL1 = "SELECT Fname, Lname FROM Doctor WHERE DocUsername = ?";
		List<String> list = new ArrayList<String>();
		String doctorName = currentDoctor.cd.getDoctorUsername();
		
		
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		try (PreparedStatement stmt = conn.prepareStatement(SQL);
				PreparedStatement stmt1 = conn.prepareStatement(SQL1)){
			
			stmt.setString(1, this.inputPatientField.getText());
			stmt1.setString(1, doctorName);
			rs = stmt.executeQuery();
			rs1 = stmt1.executeQuery();

			while(rs.next() && rs1.next()){

				list.add(rs.getString("Name") + "     " + rs.getString("HomePhone"));
				
				System.out.println(rs.getString("Name") + rs.getString("HomePhone"));
			}
			surgerytextField.setText("Dr. " + rs1.getString("Fname") + " " + rs1.getString("Lname"));
			String[] temp = new String[list.size()];
			list.toArray(temp);
			comboPatient.setModel(new DefaultComboBoxModel(temp));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}

	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
