import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatintVisitHistoryPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private JTextField tfDoctor;
	private JTextField tfDiagnosis;
	private JTextField tfSystolic;
	private JTextField tfDiastolic;
	JTextArea textArea;
	public PatintVisitHistoryPanel() throws SQLException {

		setSize(550, 450);
		setLayout(null);
		
		JLabel lblViewVisitHistory = new JLabel("View Visit History");
		lblViewVisitHistory.setBounds(200, 28, 133, 16);
		add(lblViewVisitHistory);
		
		JLabel lblDateOfVisit = new JLabel("Date of Visit");
		lblDateOfVisit.setBounds(19, 106, 100, 16);
		add(lblDateOfVisit);
		
		String[] dates = getDates();
		final JComboBox CbDateofVisit = new JComboBox(dates);
		CbDateofVisit.setBounds(6, 134, 166, 27);
		add(CbDateofVisit);
		
		JLabel lblConsultingdoctor = new JLabel("Consulting Doctor:");
		lblConsultingdoctor.setBounds(184, 106, 133, 16);
		add(lblConsultingdoctor);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure:");
		lblBloodPressure.setBounds(184, 204, 133, 16);
		add(lblBloodPressure);
		
		JLabel lblDiagnosis = new JLabel("Diagnosis:");
		lblDiagnosis.setBounds(184, 138, 82, 34);
		add(lblDiagnosis);
		
		JLabel lblMedicationPrescribed = new JLabel("Medication Prescribed:");
		lblMedicationPrescribed.setBounds(19, 253, 156, 16);
		add(lblMedicationPrescribed);
		
		tfDoctor = new JTextField();
		tfDoctor.setBounds(365, 100, 134, 28);
		add(tfDoctor);
		tfDoctor.setColumns(10);
		
		tfDiagnosis = new JTextField();
		tfDiagnosis.setBounds(365, 144, 134, 28);
		add(tfDiagnosis);
		tfDiagnosis.setColumns(10);
		
		JLabel lblSystolic = new JLabel("Systolic");
		lblSystolic.setBounds(296, 204, 49, 16);
		add(lblSystolic);
		
		tfSystolic = new JTextField();
		tfSystolic.setBounds(351, 198, 55, 28);
		add(tfSystolic);
		tfSystolic.setColumns(10);
		
		JLabel lblDiastolic = new JLabel("Diastolic");
		lblDiastolic.setBounds(418, 204, 61, 16);
		add(lblDiastolic);
		
		tfDiastolic = new JTextField();
		tfDiastolic.setBounds(489, 198, 55, 28);
		add(tfDiastolic);
		tfDiastolic.setColumns(10);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayVistInfo(CbDateofVisit.getSelectedItem().toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("parse problem");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("sql problem");
				}
			}
		});
		btnCheck.setBounds(418, 394, 117, 29);
		add(btnCheck);
		
		textArea = new JTextArea();
		textArea.setBounds(53, 281, 415, 87);
		add(textArea);
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public String[] getDates() throws SQLException{

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> datesList = new ArrayList<String>();
		String SQL = "SELECT DateofVisit FROM Visit WHERE PatUsername = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				System.out.println(dateFormat.format(rs.getDate(1)));
				datesList.add(dateFormat.format(rs.getDate(1)));
			}
		}
		String[] dates = (String[]) datesList.toArray(new String[datesList.size()]);
		return dates;
	}
	
	public void displayVistInfo(String date) throws SQLException, ParseException{
		
		System.out.println(date);
		String DocUsername = "";
		float DiastolicPressure = 0;
		float SystolicPressure = 0;
		int visitID = 0;
		String SQL = "SELECT * FROM Visit WHERE DateofVisit = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, date);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				DocUsername = rs.getString("DocUsername");
				DiastolicPressure = rs.getFloat("DiastolicPressure");
				SystolicPressure = rs.getFloat("SystolicPressure");
				visitID = rs.getInt("VisitID");
				tfDiastolic.setText("" + DiastolicPressure);
				tfSystolic.setText("" + SystolicPressure);
				System.out.println("DocUsername = " + DocUsername + "DP = " + DiastolicPressure
						+ "SP = " + SystolicPressure);
			}
		}
		
		// get doctor's name
		String Doctor = "";
		String SQL1 = "SELECT Lname FROM Doctor WHERE DocUsername = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);) {
			stmt.setString(1, DocUsername);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				Doctor = rs.getString("Lname");
				System.out.println("Doctor is " + Doctor);
				tfDoctor.setText("Dr." + Doctor);
			}
		}
		
		// get diagnosis
		String diagnosis = "";
		String SQL2 = "SELECT * FROM Visit_Diagnosis WHERE VisitID = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL2);) {
			stmt.setInt(1, visitID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				diagnosis = rs.getString("Diagnosis");
				System.out.println("Diagnosis is " + diagnosis);
				tfDiagnosis.setText("" + diagnosis);
			}
		}
		
		// get prescription information
		String medname = "";
		String Dosage = "";
		String Duration = "";
		String Notes = "";
		String resultText = "";
		String SQL3 = "SELECT * FROM Prescription WHERE VisitID = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL3);) {
			stmt.setInt(1, visitID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				medname = rs.getString("MedicineName");
				Dosage = rs.getString("Dosage");
				Duration = rs.getString("Duration");
				Notes = rs.getString("Notes");
				String text = "Medicine Name: " + medname + "    " + "Dosage: " + Dosage 
						+ "    " + "Duration: " + Duration + "     " + "Notes:" + Notes + "\n";
				resultText += text;
			}
		}
		
		textArea.setText(resultText);
	}
}
