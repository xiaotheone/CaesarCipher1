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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;


public class RecordVisitPanel extends JPanel{

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField visitdateField;
	private JTextField patiennameField;
	private JTextField systolicField;
	private JTextField diastolicField;
	private JTextField drugNameField;
	private JTextArea diagnosisArea;
	private JComboBox comboDay;
	private JComboBox comboMonth;
	private JComboBox comboDosage;
	private JTextArea notesArea;
	private String inputString = "";
	private String patientName = "";
	private String patientPhone = "";
	
	@SuppressWarnings("unchecked")
	public RecordVisitPanel( String input){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		inputString = input;
		patientName = inputString.split(" ")[0] + " " + inputString.split(" ")[1];
		patientPhone = inputString.split("-")[1];
		
		
		JLabel lblRecordAVisit = new JLabel("Record a Visit");
		lblRecordAVisit.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblRecordAVisit.setBounds(206, 25, 116, 36);
		add(lblRecordAVisit);
		
		JLabel lblDateOfVisit = new JLabel("Date of Visit:");
		lblDateOfVisit.setBounds(40, 83, 82, 16);
		add(lblDateOfVisit);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(40, 111, 101, 16);
		add(lblPatientName);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure:");
		lblBloodPressure.setBounds(40, 142, 101, 16);
		add(lblBloodPressure);
		
		JLabel lblNewLabel = new JLabel("Systolic");
		lblNewLabel.setBounds(142, 142, 57, 16);
		add(lblNewLabel);
		
		visitdateField = new JTextField();
		visitdateField.setEditable(true);
		visitdateField.setBounds(134, 77, 134, 28);
		add(visitdateField);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		visitdateField.setText(dateFormat.format(date).toString());
		visitdateField.setColumns(10);
		
		patiennameField = new JTextField();
		patiennameField.setEditable(false);
		patiennameField.setColumns(10);
		patiennameField.setBounds(134, 105, 134, 28);
		patiennameField.setText(patientName);
		add(patiennameField);
		
		systolicField = new JTextField();
		systolicField.setColumns(10);
		systolicField.setBounds(193, 136, 44, 28);
		add(systolicField);
		
		JLabel lblDiastolic = new JLabel("Diastolic");
		lblDiastolic.setBounds(249, 142, 61, 16);
		add(lblDiastolic);
		
		diastolicField = new JTextField();
		diastolicField.setColumns(10);
		diastolicField.setBounds(308, 136, 44, 28);
		add(diastolicField);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis:");
		lblDiagnosis.setBounds(40, 170, 82, 16);
		add(lblDiagnosis);
		
		diagnosisArea = new JTextArea();
		diagnosisArea.setBounds(142, 170, 210, 36);
		add(diagnosisArea);
		
		JLabel lblDrugName = new JLabel("Drug Name:");
		lblDrugName.setBounds(40, 247, 82, 16);
		add(lblDrugName);
		
		drugNameField = new JTextField();
		drugNameField.setBounds(142, 241, 134, 28);
		add(drugNameField);
		drugNameField.setColumns(10);
		
		JLabel lblDosage = new JLabel("Dosage:");
		lblDosage.setBounds(40, 275, 61, 16);
		add(lblDosage);
		
		JLabel lblNewLabel_1 = new JLabel("Duration:");
		lblNewLabel_1.setBounds(40, 303, 61, 16);
		add(lblNewLabel_1);
		
		String[] dosageList = {"1","2","3","4","5"};
		comboDosage = new JComboBox(dosageList);
		comboDosage.setBounds(142, 271, 52, 27);
		add(comboDosage);
		
		JLabel lblPerDay = new JLabel("per day");
		lblPerDay.setBounds(206, 275, 61, 16);
		add(lblPerDay);
		
		String[] monthList = {"0","1","2","3","4","5"};
		comboMonth = new JComboBox(monthList);
		comboMonth.setBounds(142, 299, 52, 27);
		add(comboMonth);
		
		JLabel lblMonth = new JLabel("Months");
		lblMonth.setBounds(193, 303, 53, 16);
		add(lblMonth);
		
		String[] dayList = {"0","5","10","15","20","25","30"};
		comboDay = new JComboBox(dayList);
		comboDay.setBounds(258, 299, 52, 27);
		add(comboDay);
		
		JLabel lblDay = new JLabel("Days");
		lblDay.setBounds(308, 303, 61, 16);
		add(lblDay);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(40, 337, 61, 16);
		add(lblNotes);
		
		notesArea = new JTextArea();
		notesArea.setLineWrap(true);
		notesArea.setBounds(134, 337, 218, 73);
		add(notesArea);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(427, 381, 117, 29);
		add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub
				try {
					String username = getPatientUsername(patientName, patientPhone);
					recordData(username, isFirstVist(username));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(6, 415, 96, 29);
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
		
		
	}
	
	public String getPatientUsername(String patientName, String patientPhone) throws SQLException{
		
		String sql = "SELECT PatientUsername FROM Patient WHERE Name = ? AND HomePhone = ?";
		ResultSet rs = null;
		String username = "";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			System.out.println(patientName + " " + patientPhone);
			stmt.setString(1, patientName);
			stmt.setString(2, patientPhone);
			
			
			rs = stmt.executeQuery();
			
			if(rs.next()){
				System.out.println("Username: " + rs.getString("PatientUsername"));
				username =  rs.getString("PatientUsername");
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		return username;
	}
	
	public boolean isFirstVist(String patientName) throws SQLException{
		String sql = "SELECT VisitID FROM Visit WHERE PatUsername = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, getPatientUsername(patientName, patientName));
			System.out.println("Patient name in isfirstvisit: " + patientName);
			ResultSet rs = null;
			rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("Not the first visit");
				return false;
			} else {
				System.out.println("its the first visit");

				return true;
			}

		}
			
	}
	public void recordData(String patusername, boolean isFirstVisit) throws SQLException{
		//insert into visit table
		String sql = "INSERT INTO Visit(VisitID, DocUsername,PatUsername,DateofVisit,DiastolicPressure, SystolicPressure, BillingAmount) VALUES(?,?,?,?,?,?,?) ";
		String sql2 = "INSERT INTO Visit_Diagnosis(VisitID, Diagnosis) VALUES(?,?)";
		String sql3 = "SELECT VisitID FROM Visit";
		String sql4 = "INSERT INTO Prescription VALUES(?,?,?,?,?,?)";
		int visitAmount = 0;
		String pName = getPatientUsername(patientName, patientPhone);
		String dName = currentDoctor.cd.getDoctorUsername();
		
		if(isFirstVisit){
			System.out.println("setting amount to 80");
			
			visitAmount =  200;
			if(isLowIncome( getPatientUsername(patientName, patientPhone))){
				visitAmount = 160;
			}
		}else if (!isFirstVisit) {
			System.out.println("setting amount to 100");
			
			visitAmount = 150;
			if(isLowIncome( getPatientUsername(patientName, patientPhone))){
				visitAmount = 120;
			}
		}
		
		ResultSet rs = null;
		int lastID = -1;
		
		try(PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt2 = conn.prepareStatement(sql2);
				PreparedStatement stmt3 = conn.prepareStatement(sql3);
				PreparedStatement stmt4 = conn.prepareStatement(sql4)) {

			rs = stmt3.executeQuery();

			if (rs.next()) {
				rs.last();
				lastID = rs.getInt("VisitID") + 1;
			}

			System.out.println("lastID: " + lastID);
			stmt.setInt(1, lastID);
			stmt.setString(2, currentDoctor.cd.getDoctorUsername());
			stmt.setString(3, getPatientUsername(patientName, patientPhone));
			stmt.setString(4, visitdateField.getText());
			stmt.setString(5, diastolicField.getText());
			stmt.setString(6, systolicField.getText());
			stmt.setInt(7, visitAmount);
			
			
			if (lastID != -1) {
				stmt2.setInt(1, lastID);
				stmt2.setString(2, diagnosisArea.getText());
				stmt4.setInt(1, lastID);
				stmt4.setString(2, drugNameField.getText());
				stmt4.setString(3, comboDosage.getSelectedItem().toString());
				stmt4.setString(4, new String(comboMonth.getSelectedItem().toString() + "Months " 
												+ comboDay.getSelectedItem().toString() + "Days "));
				stmt4.setString(5, notesArea.getText());
				stmt4.setString(6, "No");
				
				
			}else{
				System.err.println("It is not a valid VisitID.");
			}
			

			int affected = stmt.executeUpdate();
			int affected2 = stmt2.executeUpdate();
			int affected3 = stmt4.executeUpdate();
			
			if (affected == 1 && affected2 == 1 && affected3 == 1) {
				System.out.println("data imported.");
				deleteAppointment(dName, pName);
				JOptionPane.showMessageDialog(getParent(), "data imported");
			} else {
				System.out.println("data  not imported.");
				JOptionPane.showMessageDialog(getParent(), "data not imported");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}
	
	public boolean isLowIncome(String patientName) throws SQLException{
		String sql = "SELECT AnnualIncome FROM Patient WHERE PatientUsername = ?";
		ResultSet rs = null;
		boolean isLowincome = false;
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, patientName);
			
			rs = stmt.executeQuery();
			System.out.println("Checking lowincome..."  );
			if (rs.next()){
				if (rs.getInt("AnnualIncome") < 25000) {
					isLowincome = true;
					System.out.println("patient is low income.");
				}else{
					System.out.println("patient is high income.");

				}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		
		return isLowincome;
	}
	
	public void deleteAppointment(String dUsername, String pUsername){
		
		String SQL = "DELETE FROM Appointments WHERE DocUsername = ? AND PatientUsername = ? ";
		
			try (PreparedStatement stmt = conn.prepareStatement(SQL)){
				
				stmt.setString(1, dUsername);
				stmt.setString(2, pUsername);
				
				int affected = stmt.executeUpdate();
				if (affected == 1){
					System.out.println("data affected, appoinment deleted.");
				}else{
					System.out.println("data not affected, appointment not deleted.");
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
