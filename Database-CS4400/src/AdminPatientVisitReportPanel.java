import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class AdminPatientVisitReportPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	JComboBox CbMonth;
	JComboBox CbYear;
	public AdminPatientVisitReportPanel() throws SQLException{
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPatientVisitReport = new JLabel("Patient Visit Report ");
		lblPatientVisitReport.setBounds(196, 28, 153, 16);
		add(lblPatientVisitReport);
		
		JLabel lblSelectMonth = new JLabel("Select Month:");
		lblSelectMonth.setBounds(97, 76, 101, 16);
		add(lblSelectMonth);
		
		String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		CbMonth = new JComboBox(month);
		CbMonth.setBounds(221, 72, 63, 27);
		add(CbMonth);
		
		String[] year = {"2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005" };
		CbYear = new JComboBox(year);
		CbYear.setBounds(311, 72, 91, 27);
		add(CbYear);
		
		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					patientVisitReport();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerateReport.setBounds(391, 403, 153, 29);
		add(btnGenerateReport);
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
		}
		
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public void patientVisitReport() throws SQLException{
		
		
		// get numbers of patient each doctor has seem
		String Fname, Lname = "";
		int patientNum = 0;
		String SQL = "SELECT Fname, Lname, Count(*) AS PatientSeem "
					+ "FROM Doctor JOIN Visit "
					+ "ON Doctor.DocUsername = Visit.DocUsername "
					+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
					+ "GROUP BY Doctor.DocUsername";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				patientNum = rs.getInt("PatientSeem");
				
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   Patient Seem: " + patientNum );
			}
		}
		
		// get numbers of prescription each doctor has prescribed
		int prescriptionNum = 0;
		String SQL1 = "SELECT Fname, Lname, COUNT(*) AS PrescriptionNum "
					   + "FROM Doctor JOIN Visit ON Doctor.DocUsername = Visit.DocUsername "
					   + "JOIN Prescription ON Visit.VisitID = Prescription.VisitID "
					   + "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
					   + "GROUP BY Doctor.DocUsername";
					  
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				prescriptionNum = rs.getInt("PrescriptionNum");
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   Number of Prescription: " + prescriptionNum );
			}
		}
		
		
		//get visit bill
		int visitBill = 0;
		String SQL2 = "SELECT Fname, Lname, SUM(BillingAmount) AS TotalBill "
						+ "FROM Doctor JOIN Visit ON Doctor.DocUsername = Visit.DocUsername "
						+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
						+ "GROUP BY Doctor.DocUsername";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL2);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				visitBill = rs.getInt("TotalBill");
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   total Visit Bill: " + visitBill );
			}
		}
		
		//get sugery bill
		int surgeryBill = 0;
		String SQL3 = "SELECT Fname, Lname, SUM(CostofSurgery) AS TotalSurgeryBill "
						+ "FROM Doctor JOIN Performs on Doctor.DocUsername = Performs.DocUsername "
						+ "JOIN Visit ON Doctor.DocUsername = Visit.DocUsername "
						+ "JOIN Surgery ON Performs.CPTCode = Surgery.CPTCode "
						+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
						+ "GROUP BY Doctor.DocUsername";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL3);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				surgeryBill = rs.getInt("TotalSurgeryBill");
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   total Surgery Bill: " + surgeryBill );
			}
		}
	}
}
