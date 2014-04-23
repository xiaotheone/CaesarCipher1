import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.TableColumn;


public class AdminSurgeryPerformedReportPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private String[] columnNames = new String[5];
	
	private JScrollPane scrollPane = new JScrollPane();
	private Object[][] data;
	private int rows=0;
	private JTable table;
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
				try {
					remove(scrollPane);
					displayReport();
					revalidate();
					repaint();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerateReport.setBounds(362, 395, 165, 29);
		add(btnGenerateReport);
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
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public void displayReport() throws SQLException{
		addTable();
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
				
				data[rs.getRow()-1][0]= new String (surgeryType);
				cptCode = rs.getString("CPTCode");
				
				data[rs.getRow()-1][1]= new String (cptCode);
				numPro = rs.getInt("NoofProcedures");
				
				data[rs.getRow()-1][2]= new Integer (numPro);
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
				data[rs.getRow()-1][4]= new Integer (bill);
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
		
				
				data[rs.getRow()-1][3]= new Integer (numDoctor);
			}
			System.out.println();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void getrows() throws SQLException {

		Calendar now = Calendar.getInstance();
		int monthNow = now.get(Calendar.MONTH);
		int yearNow = now.get(Calendar.YEAR);
		int monthBefore = 0;
		int yearBefore = 0;
		if (monthNow - 2 <= 0) {
			monthBefore = (monthNow - 2) + 12;
			yearBefore = yearNow - 1;
		} else {
			monthBefore = monthNow - 2;
			yearBefore = yearNow;
		}

		// get surgery type, cpt code, and number of procedure performed
		String surgeryType, cptCode = "";
		int numPro = 0;

		String SQL = " SELECT SurgeryType, Surgery.CPTCode, COUNT(*) AS NoofProcedures "
				+ "FROM Surgery JOIN Performs ON Surgery.CPTCode = Performs.CPTCode "
				+ "WHERE YEAR(SurgeryStartTime) BETWEEN ? AND ? AND MONTH(SurgeryStartTime) BETWEEN ? AND ? "
				+ "GROUP BY SurgeryType";

		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setInt(1, yearBefore);
			stmt.setInt(2, yearNow);
			stmt.setInt(3, monthBefore);
			stmt.setInt(4, monthNow);
			ResultSet rs = stmt.executeQuery();

			rs.last();
			rows = rs.getRow();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addTable() throws SQLException {

		columnNames[0] = "Surgery Type";
		columnNames[1] = "CPT CODE";
		columnNames[2] = "# of Procedures";
		columnNames[3] = "# Doctors Performing ";
		columnNames[4] = "Total Billing($)";
		getrows();
		data = new Object[rows][5];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++)
				data[i][j] = new String("0");
		}
		table = new JTable(data, columnNames);
		table.setBounds(37, 79, 449, 344);

		table.setFillsViewportHeight(true);

		table.getTableHeader();

		table.setRowHeight(30);

		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, table.getHeight()));
		scrollPane.setBounds(37, 129, 500, 264);
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);

			column.setPreferredWidth(100);

		}

		this.add(scrollPane);
	}
}
