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
public class CreateBillPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private JTextField tfPatientName;
	public CreateBillPanel() throws SQLException {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblBilling = new JLabel("Billing");
		lblBilling.setBounds(232, 28, 61, 16);
		add(lblBilling);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(70, 73, 87, 16);
		add(lblPatientName);
		
		tfPatientName = new JTextField();
		tfPatientName.setBounds(187, 67, 134, 28);
		add(tfPatientName);
		tfPatientName.setColumns(10);
		
		JButton btnCreateBill = new JButton("Create Bill");
		btnCreateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createBill();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnCreateBill.setBounds(357, 68, 116, 29);
		add(btnCreateBill);
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
	
	public void createBill() throws SQLException{
		
		String name = "";
		String phone = "";
		String date = "";
		int billAmt = 0;
		String surgeryType = "";
		int surgeryCost = 0;
		int totalVisitCost = 0;
		int totalSurgeryCost = 0;
		
		int sum = 0;
		String SQL = "SELECT Name, HomePhone, DateofVisit, BillingAmount "
					+ "FROM Patient AS P, Visit AS V "
					+ "WHERE P.PatientUsername = V.PatUsername AND P.Name = ? "
					+ "ORDER BY V.DateofVisit ASC";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, tfPatientName.getText());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
			    name = rs.getString("Name");
				phone = rs.getString("HomePhone");
				date = rs.getString("DateofVisit");
				billAmt = rs.getInt("BillingAmount");
				System.out.println("Name: " + tfPatientName.getText()  + "     Phone: " + phone + "    date: " + date + "    Bill: " + billAmt);
				totalVisitCost += billAmt;
			}
			System.out.println("total visit cost: " + totalVisitCost);
		} catch (SQLException e){
			e.printStackTrace();
		}
	
	
		String SQL1 = "SELECT SurgeryType, CostofSurgery "
						+ "FROM Performs AS Per, Patient AS P, Surgery AS S "
						+ "WHERE S.CPTCode = Per.CPTCode AND Per.PatUsername = P.PatientUsername AND Name = ? AND HomePhone = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);) {
			stmt.setString(1, name);
			stmt.setString(2, phone);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				surgeryType = rs.getString("SurgeryType");
				surgeryCost = rs.getInt("CostofSurgery");
				System.out.println("Surgery Type: " + surgeryType + "     cost: " + surgeryCost);
				totalSurgeryCost += surgeryCost;
			}
			System.out.println("total surgery cost: " + totalSurgeryCost);
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		sum = totalVisitCost + totalSurgeryCost;
		System.out.println("total cost is " + sum);
	}

}





