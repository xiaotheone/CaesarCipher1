import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	
	
	private JLabel lblPatientPhone;
	private JLabel lblPatientName_1;
	private JLabel lblTotalCost;
	private Object[][] data1;
	private int rows1=0;
	private JTable table1 = new JTable();
	private JScrollPane scrollPane1 = new JScrollPane();
	private String[] columnNames1 = new String[2];
	
	private Object[][] data2;
	private int rows2=0;
	private JTable table2 = new JTable();
	private JScrollPane scrollPane2 = new JScrollPane();
	private String[] columnNames2 = new String[2];
	
	
	
	
	
	
	
	
	
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
					remove(scrollPane1);
					remove(scrollPane2);
					createBill();
					revalidate();
					repaint();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnCreateBill.setBounds(357, 68, 116, 29);
		add(btnCreateBill);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				add(new AdminHomepage());
			}
		});
		lblBack.setBounds(23, 415, 61, 16);
		add(lblBack);
		
		lblTotalCost = new JLabel("total cost:");
		lblTotalCost.setBounds(323, 305, 193, 38);
		add(lblTotalCost);
		
		 lblPatientName_1 = new JLabel("Patient Name :");
		lblPatientName_1.setBounds(70, 122, 172, 28);
		add(lblPatientName_1);
		
		 lblPatientPhone = new JLabel("Patient Phone:");
		lblPatientPhone.setBounds(252, 122, 172, 28);
		add(lblPatientPhone);
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
		addTable();
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
				data1[rs.getRow()-1][0]= new String(date);
					data1[rs.getRow()-1][1]= new Integer(billAmt);
					totalVisitCost += billAmt;
			}
			lblPatientName_1.setText("Patient Name: " + name);
			lblPatientPhone.setText("Patient Phone : " +phone);
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
				data2[rs.getRow()-1][0]= new String(surgeryType);
				data2[rs.getRow()-1][1]= new Integer(surgeryCost);
				
				System.out.println("Surgery Type: " + surgeryType + "     cost: " + surgeryCost);
				totalSurgeryCost += surgeryCost;
			}
			System.out.println("total surgery cost: " + totalSurgeryCost);
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		sum = totalVisitCost + totalSurgeryCost;
		System.out.println("total cost is " + sum);
		lblTotalCost.setText("Total cost: " + sum); 
	}
	public int getrows1(){

		int i = 0;

		String SQL = "SELECT Name, HomePhone, DateofVisit, BillingAmount "
					+ "FROM Patient AS P, Visit AS V "
					+ "WHERE P.PatientUsername = V.PatUsername AND P.Name = ? "
					+ "ORDER BY V.DateofVisit ASC";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, tfPatientName.getText());
			ResultSet rs = stmt.executeQuery();
			rs.last();
			i= rs.getRow();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return i;
	}

	public int getrows2() {
		int i = 0;
		String SQL1 = "SELECT SurgeryType, CostofSurgery "
				+ "FROM Performs AS Per, Patient AS P, Surgery AS S "
				+ "WHERE S.CPTCode = Per.CPTCode AND Per.PatUsername = P.PatientUsername AND Name = ? ";
		try (PreparedStatement stmt = conn.prepareStatement(SQL1);) {
			stmt.setString(1, tfPatientName.getText());

			ResultSet rs = stmt.executeQuery();
			rs.last();
			i = rs.getRow();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public void addTable() throws SQLException{

	    columnNames1[0]= "Visits";
	    columnNames1[1]= "Cost ($)";
	  
	    rows1 =getrows1();
		data1 = new Object[rows1][2];
		for ( int i =0; i<data1.length;i++){
			for ( int j =0; j<data1[i].length;j++)
				data1[i][j] = new String ("0");
		}
		table1 = new JTable(data1, columnNames1);
		table1.setBounds(37, 79, 449, 344);

		table1.setFillsViewportHeight(true);

		table1.getTableHeader();
	
		table1.setRowHeight(30);
		
		   scrollPane1 = new JScrollPane(table1);
		  scrollPane1.setPreferredSize(new Dimension(250,table1.getHeight()));
		  scrollPane1.setBounds(37, 159,225,132);
		    TableColumn column = null;
		    for (int i = 0; i < 2; i++) {
		        column = table1.getColumnModel().getColumn(i);
		      
		            column.setPreferredWidth(100);
		     
		        
		    }  

		this.add(scrollPane1);
		
		
	    columnNames2[0]= "Surgery";
	    columnNames2[1]= "Cost ($)";
	  
	    rows2 =getrows2();
		data2 = new Object[rows2][2];
		for ( int i =0; i<data2.length;i++){
			for (int j = 0; j < data2[i].length; j++)
				data2[i][j] = new String("0");
		}
		table2 = new JTable(data2, columnNames2);

		table2.setFillsViewportHeight(true);

		table2.getTableHeader();

		table2.setRowHeight(30);

		scrollPane2 = new JScrollPane(table2);
		scrollPane2.setPreferredSize(new Dimension(250, table2.getHeight()));
		scrollPane2.setBounds(262, 159, 225, 132);
		TableColumn column2 = null;
		for (int i = 0; i < 2; i++) {
			column2 = table2.getColumnModel().getColumn(i);

			column2.setPreferredWidth(100);

		}

		this.add(scrollPane2);
	}
}





