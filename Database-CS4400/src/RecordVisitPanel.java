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

	
	
	public RecordVisitPanel(final String patientName){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
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
		visitdateField.setEditable(false);
		visitdateField.setBounds(134, 77, 134, 28);
		add(visitdateField);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date).toString());
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
		
		JTextArea diagnosisArea = new JTextArea();
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
		
		JComboBox comboDosage = new JComboBox();
		comboDosage.setBounds(142, 271, 52, 27);
		add(comboDosage);
		
		JLabel lblPerDay = new JLabel("per day");
		lblPerDay.setBounds(206, 275, 61, 16);
		add(lblPerDay);
		
		JComboBox comboMonth = new JComboBox();
		comboMonth.setBounds(142, 299, 52, 27);
		add(comboMonth);
		
		JLabel lblMonth = new JLabel("Months");
		lblMonth.setBounds(193, 303, 53, 16);
		add(lblMonth);
		
		JComboBox comboDay = new JComboBox();
		comboDay.setBounds(258, 299, 52, 27);
		add(comboDay);
		
		JLabel lblDay = new JLabel("Days");
		lblDay.setBounds(308, 303, 61, 16);
		add(lblDay);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(40, 337, 61, 16);
		add(lblNotes);
		
		JTextArea notesArea = new JTextArea();
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
					recordData(getPatientUsername(patientName));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton = new JButton("Add prescription");
		btnNewButton.setBounds(237, 415, 117, 29);
		add(btnNewButton);
		
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
	
	public String getPatientUsername(String patientName) throws SQLException{
		
		String sql = "SELECT PatientUsername FROM Patient WHERE Name = ?";
		ResultSet rs = null;
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, patientName);
			rs = stmt.executeQuery();
			if(rs.next()){
				return rs.getString("PatientUsername");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		return null;
	}
	
	public void recordData(String patusername) throws SQLException{
		//insert into visit table
		String sql = "INSERT INTO Visit(DocUsername,PatUsername,DateofVisit,DiastolicPressure, SystolicPressure	) VALUES(?,?,?,?,?) ";
		String sql2 = "INSERT INTO Visit_Diagnosis VALUES(?,?)";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
			
			//System.out.println(rs.getString("LAST_INSERT_ID()"));
			//stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			//stmt.setInt(1, statement..)
			//rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			System.out.println(stmt.executeQuery("SELECT LAST_INSERT_ID() FROM Visit").toString());
			stmt.setString(1, "bchen80");
			stmt.setString(2, "john20");
			stmt.setString(3, visitdateField.getText());
			stmt.setString(4, diastolicField.getText());
			stmt.setString(5, systolicField.getText());
			
			
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				System.out.println("data imported.");
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
