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
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class AdminPatientVisitReportPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	JComboBox CbMonth;
	JComboBox CbYear;
	
	private Object[][] data;
	private int rows=0;
	private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane();
	private String[] columnNames = new String[4];
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
				
					remove(scrollPane);
					
					patientVisitReport();
					revalidate();
					repaint();
	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerateReport.setBounds(391, 403, 153, 29);
		add(btnGenerateReport);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				add(new AdminHomepage());
			}
		});
		lblBack.setBounds(36, 408, 61, 16);
		add(lblBack);
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
	
	public int getrows() throws SQLException {

		String SQL = "SELECT Fname, Lname, Count(*) AS PatientSeem "
					+ "FROM Doctor JOIN Visit "
					+ "ON Doctor.DocUsername = Visit.DocUsername "
					+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
					+ "GROUP BY Doctor.DocUsername";
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			
			rs.last();
			return rs.getRow();
		
		}
	}
	public void patientVisitReport() throws SQLException{
		addTable();
		
		// get numbers of patient each doctor has seem
		String Fname, Lname = "";
		int patientNum = 0;
		String SQL = "SELECT Fname, Lname, Count(*) AS PatientSeem "
					+ "FROM Doctor JOIN Visit "
					+ "ON Doctor.DocUsername = Visit.DocUsername "
					+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
					+ "GROUP BY Doctor.DocUsername";
		Object[][] s = new Object[getrows()][2]; 
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				patientNum = rs.getInt("PatientSeem");
				s[rs.getRow()-1][0]= new String(Fname +" "+ Lname);
				
				s[rs.getRow()-1][1] = new Integer(patientNum);
				
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   Patient Seem: " + patientNum );
			}
			for(int x =0;x<s.length;x++){
				for(int y =0;y<s[x].length;y++){
					System.out.println(s[x][y]);
					data[x][y]= s[x][y];
				}
			}
		}
		
		// get numbers of prescription each doctor has prescribed
		int prescriptionNum = 0;
		String SQL1 = "SELECT Fname, Lname, COUNT(*) AS PrescriptionNum "
					   + "FROM Doctor JOIN Visit ON Doctor.DocUsername = Visit.DocUsername "
					   + "JOIN Prescription ON Visit.VisitID = Prescription.VisitID "
					   + "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
					   + "GROUP BY Doctor.DocUsername";
					  
		Object[] s2 = new Object[getrows()]; 
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);){
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				prescriptionNum = rs.getInt("PrescriptionNum");
			
				System.out.println("Doctor Name: " + Fname + " " + Lname + "   Number of Prescription: " + prescriptionNum );
				s2[rs.getRow()-1]= new Integer(prescriptionNum);
				
				for(int x =0;x<s2.length;x++){

						data[x][2]= s2[x];
					}
				}
			}
		
		
		
		
		//get visit bill
		int visitBill = 0;
		String SQL2 = "SELECT Fname, Lname, SUM(BillingAmount) AS TotalBill "
						+ "FROM Doctor JOIN Visit ON Doctor.DocUsername = Visit.DocUsername "
						+ "WHERE MONTH(DateofVisit) = ? AND YEAR(DateofVisit) = ? "
						+ "GROUP BY Doctor.DocUsername";

		int[] s3 = new int[getrows()];
		try (PreparedStatement stmt = conn.prepareStatement(SQL2);) {
			stmt.setString(1, CbMonth.getSelectedItem().toString());
			stmt.setString(2, CbYear.getSelectedItem().toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Fname = rs.getString("Fname");
				Lname = rs.getString("Lname");
				visitBill = rs.getInt("TotalBill");
				System.out.println("Doctor Name: " + Fname + " " + Lname
						+ "   total Visit Bill: " + visitBill);

				s3[rs.getRow() - 1] = visitBill;

			}
			for (int x = 0; x < s3.length; x++) {

				data[x][3] = s3[x];
			}
		}

	}

	public void addTable() throws SQLException {

		columnNames[0] = "Doctor Name";
		columnNames[1] = "No of patient seen";
		columnNames[2] = "prescription written";
	    columnNames[3]= "Total billing $";
	    rows =getrows();
		data = new Object[rows][4];
		for ( int i =0; i<data.length;i++){
			for ( int j =0; j<data[i].length;j++)
				data[i][j] = new String ("0");
		}
		table = new JTable(data, columnNames);
		table.setBounds(37, 79, 449, 344);

		table.setFillsViewportHeight(true);

		table.getTableHeader();
	
		table.setRowHeight(30);
		
		   scrollPane = new JScrollPane(table);
		  scrollPane.setPreferredSize(new Dimension(500,table.getHeight()));
		  scrollPane.setBounds(37, 129, 449, 264);
		    TableColumn column = null;
		    for (int i = 0; i < 3; i++) {
		        column = table.getColumnModel().getColumn(i);
		        if (i == 2) {
		            column.setPreferredWidth(100);
		        } else {
		            column.setPreferredWidth(100);
		        }
		    }  

		this.add(scrollPane);
	}
}
