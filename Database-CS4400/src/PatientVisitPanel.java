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
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;


public class PatientVisitPanel extends JPanel{

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField txtEnterName;
	private JTextField txtPhoneNumber;
	private JTextField textField_2;
	private JTextField systolicField;
	private JTextField diastolicField;
	private JComboBox comboVisit;
	private int visitID = 0;
	private JTextArea diagnosisArea;
	private JButton btnSelect;
	private JTextArea medicationArea;
	private JComboBox comboPatient;

	public PatientVisitPanel(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPatientVistHistory = new JLabel("Patient Vist History");
		lblPatientVistHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPatientVistHistory.setBounds(193, 30, 148, 29);
		add(lblPatientVistHistory);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(26, 81, 45, 16);
		add(lblName);
		
		txtEnterName = new JTextField();
		txtEnterName.setBounds(71, 75, 134, 28);
		add(txtEnterName);
		txtEnterName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Phone:");
		lblNewLabel.setBounds(229, 81, 61, 16);
		add(lblNewLabel);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(277, 75, 134, 28);
		add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(423, 76, 95, 29);
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
		
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(18, 117, 95, 16);
		add(lblPatientName);
		
		JLabel lblPatientPhone = new JLabel("Phone Number");
		lblPatientPhone.setBounds(125, 117, 95, 16);
		add(lblPatientPhone);
		
		JButton btnView1 = new JButton("View");
		btnView1.setBounds(266, 144, 75, 29);
		add(btnView1);

		
		JButton btnRecordAVisit1 = new JButton("Record a Visit");
		btnRecordAVisit1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboPatient.getSelectedItem().toString() != null){
					removeAll();
					add(new RecordVisitPanel(comboPatient.getSelectedItem().toString()));
				}
			}
		});
		btnRecordAVisit1.setBounds(353, 144, 114, 29);
		add(btnRecordAVisit1);
		
		JLabel lblDateOfVisit_1 = new JLabel("Date of Visit");
		lblDateOfVisit_1.setBounds(158, 221, 82, 16);
		add(lblDateOfVisit_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(256, 215, 134, 28);
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure");
		lblBloodPressure.setBounds(158, 255, 95, 16);
		add(lblBloodPressure);
		
		systolicField = new JTextField();
		systolicField.setEditable(false);
		systolicField.setBounds(322, 249, 54, 28);
		add(systolicField);
		systolicField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Systolic:");
		lblNewLabel_1.setBounds(265, 255, 61, 16);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Diastolic:");
		lblNewLabel_2.setBounds(388, 255, 69, 16);
		add(lblNewLabel_2);
		
		diastolicField = new JTextField();
		diastolicField.setEditable(false);
		diastolicField.setColumns(10);
		diastolicField.setBounds(451, 249, 54, 28);
		add(diastolicField);
		
		JLabel lblNewLabel_3 = new JLabel("Diagnosis:");
		lblNewLabel_3.setBounds(158, 298, 82, 16);
		add(lblNewLabel_3);
		
		diagnosisArea = new JTextArea();
		diagnosisArea.setEditable(false);
		diagnosisArea.setBounds(252, 298, 124, 29);
		add(diagnosisArea);
		
		JLabel lblNewLabel_4 = new JLabel("Medication Prescrebed");
		lblNewLabel_4.setBounds(158, 337, 75, 28);
		add(lblNewLabel_4);
		
		
		String[] tableColumn = {"Medicine Name", "Dosage", "Duration", "Notes"};
		int numRows = 5 ;
		DefaultTableModel model = new DefaultTableModel(numRows, tableColumn.length) ;
		model.setColumnIdentifiers(tableColumn);
		
		JLabel label = new JLabel("Date of Visit");
		label.setBounds(26, 227, 82, 16);
		add(label);
		
		comboVisit = new JComboBox();
		comboVisit.setBounds(0, 251, 159, 27);
		add(comboVisit);
		
		btnSelect = new JButton("Select");
		btnSelect.setBounds(46, 293, 75, 29);
		add(btnSelect);
		
		medicationArea = new JTextArea();
		medicationArea.setLineWrap(true);
		medicationArea.setBounds(252, 339, 280, 89);
		add(medicationArea);
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(29, 403, 95, 29);
		add(btnGoBack);
		
		comboPatient = new JComboBox();
		comboPatient.setBounds(6, 145, 227, 27);
		add(comboPatient);
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

		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String visitDate = comboVisit.getSelectedItem().toString();
				try {
					System.out.println("activited select");
					getVisitInfo(visitDate);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1);
				}
			}
		});
		
	
		btnView1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (comboPatient.getSelectedItem().toString() != null) {
						System.out.println("calling viewHistory method");
					try {
						visitID = viewHistory(comboPatient.getSelectedItem().toString());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.err.println(e1);
					}
				}

			}
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void searchPatient() throws SQLException{
		
		String SQL = "SELECT Name, HomePhone FROM Patient WHERE	Name = ? OR HomePhone = ?";
		
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		String name = "";
		String phone = "";
		
		try (PreparedStatement stmt = conn.prepareStatement(SQL)){
			
			name = this.txtEnterName.getText();
			phone = this.txtPhoneNumber.getText();
			
			if (name.length() == 0 && phone.length() == 0){
				System.out.println("null name");
				rs = stmt.executeQuery("SELECT Name, HomePhone FROM Patient");
			}
			else{
				stmt.setString(1, name);
				stmt.setString(2, phone);
				rs = stmt.executeQuery();
			}

			while(rs.next()){

				list.add(rs.getString("Name") + " -" + rs.getString("HomePhone"));
				
				System.out.println(rs.getString("Name") + rs.getString("HomePhone"));
			}
			
			String[] temp = new String[list.size()];
			list.toArray(temp);
			comboPatient.setModel(new DefaultComboBoxModel(temp));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int viewHistory(String name) throws SQLException{
		
		String patientName = name.split(" ")[0] + " " + name.split(" ")[1];
		
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		ResultSet rs1 = null;
		String PatUsername = "";
		int ID = 0;
		
		String userNAMESQL = "SELECT PatientUsername FROM Patient WHERE Name = ?";
		String SQL = "SELECT VisitID, DateofVisit From Visit WHERE DocUsername = ? AND PatUsername  = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(userNAMESQL)){
			
			stmt.setString(1, patientName);
			rs = stmt.executeQuery();
			
			if(rs.next())
				PatUsername = rs.getString("PatientUsername");	
				
				System.out.println("Name:" + patientName  + "UserName: " + PatUsername);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		try (PreparedStatement stmt2 = conn.prepareStatement(SQL)){
			

			stmt2.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt2.setString(2, PatUsername);
			
			rs1 = stmt2.executeQuery();
			while(rs1.next()){
				System.out.println("added");
				list.add( rs1.getString("VisitID") + ":" + rs1.getString("DateofVisit"));
				
			}
			String[] temp = new String[list.size()];
			list.toArray(temp);
			comboVisit.setModel(new DefaultComboBoxModel(temp));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return ID;
	}
	
	public void getVisitInfo(String visitDateAndID) throws SQLException{
		
		String SQL = "SELECT Visit.DiastolicPressure, Visit.SystolicPressure, Visit_Diagnosis.Diagnosis FROM Visit, Visit_Diagnosis WHERE Visit.VisitID = ? AND DateofVisit = ?";
		String SQL1 = "SELECT MedicineName, Dosage, Duration, Notes FROM Visit, Prescription WHERE	Prescription.VisitID = ? AND DateofVisit = ?";
		ResultSet rs = null;
		ResultSet rs1 = null;
		visitID = Integer.parseInt(visitDateAndID.split(":")[0]);
		String visitDate = visitDateAndID.split(":")[1];
		
		System.out.println("visitID: " + visitID + " visitDate: " + visitDate);
		try (PreparedStatement stmt = conn.prepareStatement(SQL);
			PreparedStatement stmt1 = conn.prepareStatement(SQL1)){
			
			stmt.setInt(1, visitID);
			stmt.setString(2,visitDate);
			
			stmt1.setInt(1, visitID);
			stmt1.setString(2, visitDate);
			
			rs = stmt.executeQuery();
			rs1 = stmt1.executeQuery();
			
			if (rs.next()) {
				textField_2.setText(visitDate);
				systolicField.setText(rs.getString("SystolicPressure"));
				diastolicField.setText(rs.getString("DiastolicPressure"));
				diagnosisArea.append(rs.getString("Diagnosis"));
			}
			
			
			while(rs1.next()){
				
				medicationArea.append("MedicineName: " + rs1.getString("MedicineName") + "  " +
										"Dosage: " + rs1.getString("Dosage") + "  " + 
										"Duration: " + rs1.getString("Duration") + " " + 
										"Notes: " + rs1.getString("Notes") + "\n");
				
			}
			
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
