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
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class DoctorPerformanceReportPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private JTextField tfSpecialty1;
	private JTextField tfSpecialty2;
	private JTextField tfSpecialty3;
	private JTextField tfSpecialty4;
	private JTextField tfSpeciaty5;
	private JTextField tfSpecialty6;
	private JTextField tfRate1;
	private JTextField tfRate2;
	private JTextField tfRate3;
	private JTextField tfRate4;
	private JTextField tfRate5;
	private JTextField tfRate6;
	private JTextField tfNum1;
	private JTextField tfNum2;
	private JTextField tfNum3;
	private JTextField tfNum4;
	private JTextField tfNum5;
	private JTextField tfNum6;
	public DoctorPerformanceReportPanel() throws SQLException {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblDoctorPerformanceReport = new JLabel("Doctor Performance Report");
		lblDoctorPerformanceReport.setBounds(159, 36, 211, 16);
		add(lblDoctorPerformanceReport);
		
		JLabel lblSpecialty = new JLabel("Specialty");
		lblSpecialty.setBounds(47, 112, 61, 16);
		add(lblSpecialty);
		
		JLabel lblAverageRating = new JLabel("Average Rating");
		lblAverageRating.setBounds(192, 112, 94, 16);
		add(lblAverageRating);
		
		JLabel lblNumberOfSugeries = new JLabel("Number of Sugeries Performed");
		lblNumberOfSugeries.setBounds(330, 112, 192, 16);
		add(lblNumberOfSugeries);
		
		tfSpecialty1 = new JTextField();
		tfSpecialty1.setBounds(18, 147, 134, 28);
		add(tfSpecialty1);
		tfSpecialty1.setColumns(10);
		
		tfSpecialty2 = new JTextField();
		tfSpecialty2.setColumns(10);
		tfSpecialty2.setBounds(18, 187, 134, 28);
		add(tfSpecialty2);
		
		tfSpecialty3 = new JTextField();
		tfSpecialty3.setColumns(10);
		tfSpecialty3.setBounds(18, 227, 134, 28);
		add(tfSpecialty3);
		
		tfSpecialty4 = new JTextField();
		tfSpecialty4.setColumns(10);
		tfSpecialty4.setBounds(18, 267, 134, 28);
		add(tfSpecialty4);
		
		tfSpeciaty5 = new JTextField();
		tfSpeciaty5.setColumns(10);
		tfSpeciaty5.setBounds(18, 307, 134, 28);
		add(tfSpeciaty5);
		
		tfSpecialty6 = new JTextField();
		tfSpecialty6.setColumns(10);
		tfSpecialty6.setBounds(18, 352, 134, 28);
		add(tfSpecialty6);
		
		tfRate1 = new JTextField();
		tfRate1.setBounds(202, 147, 66, 28);
		add(tfRate1);
		tfRate1.setColumns(10);
		
		tfRate2 = new JTextField();
		tfRate2.setColumns(10);
		tfRate2.setBounds(202, 187, 66, 28);
		add(tfRate2);
		
		tfRate3 = new JTextField();
		tfRate3.setColumns(10);
		tfRate3.setBounds(202, 227, 66, 28);
		add(tfRate3);
		
		tfRate4 = new JTextField();
		tfRate4.setColumns(10);
		tfRate4.setBounds(202, 267, 66, 28);
		add(tfRate4);
		
		tfRate5 = new JTextField();
		tfRate5.setColumns(10);
		tfRate5.setBounds(202, 307, 66, 28);
		add(tfRate5);
		
		tfRate6 = new JTextField();
		tfRate6.setColumns(10);
		tfRate6.setBounds(202, 352, 66, 28);
		add(tfRate6);
		
		tfNum1 = new JTextField();
		tfNum1.setColumns(10);
		tfNum1.setBounds(370, 147, 66, 28);
		add(tfNum1);
		
		tfNum2 = new JTextField();
		tfNum2.setColumns(10);
		tfNum2.setBounds(370, 187, 66, 28);
		add(tfNum2);
		
		tfNum3 = new JTextField();
		tfNum3.setColumns(10);
		tfNum3.setBounds(370, 227, 66, 28);
		add(tfNum3);
		
		tfNum4 = new JTextField();
		tfNum4.setColumns(10);
		tfNum4.setBounds(370, 267, 66, 28);
		add(tfNum4);
		
		tfNum5 = new JTextField();
		tfNum5.setColumns(10);
		tfNum5.setBounds(370, 307, 66, 28);
		add(tfNum5);
		
		tfNum6 = new JTextField();
		tfNum6.setColumns(10);
		tfNum6.setBounds(370, 352, 66, 28);
		add(tfNum6);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
		}
		
		displayRating();
		displaySurgeryNum();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	
	public void displayRating(){
		String specialty = "";
		int rating = 0;
		String SQL = "SELECT Specialty, AVG(Rating) AS AR FROM Doctor, Doctor_Rating WHERE Doctor.DocUsername = Doctor_Rating.DocUsername GROUP BY Specialty";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				specialty = rs.getString("Specialty");
				rating = rs.getInt("AR");
				
				System.out.println("Specialty: " + specialty + "   Rating: " + rating );
			}

		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Error");
		}	
	}
	
	public void displaySurgeryNum() throws SQLException{
		int count = 0;
		String specialty = "";
		String SQL = "SELECT Specialty, COUNT(*) AS C FROM Doctor, Performs WHERE Doctor.DocUsername = Performs.DocUsername GROUP BY Specialty";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				specialty = rs.getString("Specialty");
				count = rs.getInt("C");
				
				System.out.println("Specialty: " + specialty + "   Nums: " + count );
			}
		}
	}
}
