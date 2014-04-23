import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTable;


public class AdminSurgeryPerformedReportPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	
	public AdminSurgeryPerformedReportPanel(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Surgery Performed");
		lblNewLabel.setBounds(179, 39, 143, 19);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		add(lblNewLabel);
		
		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayReport();
			}
		});
		btnGenerateReport.setBounds(362, 395, 165, 29);
		add(btnGenerateReport);
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public void displayReport(){
		
		Calendar now = Calendar.getInstance();
		int monthNow = now.get(Calendar.MONTH);
		int yearNow = now.get(Calendar.YEAR);
		int monthBefore = 0;
		int yearBefore = 0;
		if(monthNow - 2 <= 0){
			monthBefore = (monthNow - 2) + 12;
			yearBefore = yearNow - 1;
		}else{
			monthBefore = monthNow - 2;
			yearBefore = yearNow;
		}
		
		
		// get surgery type, cpt code, and number of procedure performed
		String surgeryType , cptCode  = "";
		int numPro = 0;
		String SQL = " SELECT SurgeryType, Surgery.CPTCode, COUNT(*) AS NoofProcedures "
					+ "FROM Surgery JOIN Performs ON Surgery.CPTCode = Performs.CPTCode " 
					+ "WHERE YEAR(SurgeryStartTime) BETWEEN ? AND ? AND MONTH(SurgeryStartTime) BETWEEN ? AND ? "
					+ "GROUP BY SurgeryType";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setInt(1, yearBefore);
			stmt.setInt(2, yearNow);
			stmt.setInt(3, monthBefore);
			stmt.setInt(4, monthNow);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				surgeryType = rs.getString("SurgeryType");
				cptCode = rs.getString("CPTCode");
				numPro = rs.getInt("NoofProcedures");
				System.out.println("Surgery Type: " + surgeryType + "   CPTCode: " + cptCode + "    No of Procedures: " + numPro  );
			}
			System.out.println();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		// get billing
		int bill = 0;
		String SQL1 = "SELECT SurgeryType, SUM(CostofSurgery) AS totalCost "
						+ "FROM Performs JOIN Surgery ON Performs.CPTCode = Surgery.CPTCode "
						+ "WHERE YEAR(SurgeryStartTime) BETWEEN ? AND ? AND MONTH(SurgeryStartTime) BETWEEN ? AND ? "
						+ "GROUP BY SurgeryType ";
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);) {
			stmt.setInt(1, yearBefore);
			stmt.setInt(2, yearNow);
			stmt.setInt(3, monthBefore);
			stmt.setInt(4, monthNow);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				surgeryType = rs.getString("SurgeryType");
				bill = rs.getInt("totalCost");
				System.out.println("Surgery Type: " + surgeryType + "    bill: " + bill  );
			}
			System.out.println();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		// get number of doctors
		int numDoctor = 0;
		String SQL2 = "SELECT SurgeryType, COUNT(DISTINCT DocUsername) AS NoofDoctors " 
						+ "FROM Performs JOIN Surgery ON Performs.CPTCode = Surgery.CPTCode "
						+ "WHERE YEAR(SurgeryStartTime) BETWEEN ? AND ? AND MONTH(SurgeryStartTime) BETWEEN ? AND ? "
						+ "GROUP BY SurgeryType ";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL2);) {
			stmt.setInt(1, yearBefore);
			stmt.setInt(2, yearNow);
			stmt.setInt(3, monthBefore);
			stmt.setInt(4, monthNow);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				surgeryType = rs.getString("SurgeryType");
				numDoctor = rs.getInt("NoofDoctors");
				System.out.println("Surgery Type: " + surgeryType + "    Number of Doctors: " + numDoctor  );
			}
			System.out.println();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
