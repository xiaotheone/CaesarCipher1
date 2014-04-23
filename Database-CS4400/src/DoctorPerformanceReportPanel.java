import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.JButton;

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
	private String[] columnNames = new String[3];
	
	private Object[][] data;
	private int rows=0;
	private JTable table;
	public DoctorPerformanceReportPanel() throws SQLException {
		setSize(550, 450);
		setLayout(null);
		
		
		
		
		JLabel lblDoctorPerformanceReport = new JLabel("Doctor Performance Report");
		lblDoctorPerformanceReport.setBounds(159, 36, 211, 16);
		add(lblDoctorPerformanceReport);
		
	    columnNames[0]= "Specialty";
	    columnNames[1]= "Average Rating";
	    columnNames[2]= "Surgery performed";


		getrows();
		data = new Object[rows][3];
		for ( int i =0; i<data.length;i++){
			for ( int j =0; j<data[i].length;j++)
				data[i][j] = new String ("0");
		}

		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
		}

		displayRating();
		displaySurgeryNum();

		table = new JTable(data, columnNames);
		table.setBounds(37, 79, 449, 344);

		table.setFillsViewportHeight(true);

		table.getTableHeader();
	
		table.setRowHeight(30);
	
		
		  JScrollPane scrollPane = new JScrollPane(table);
		  scrollPane.setPreferredSize(new Dimension(500,table.getHeight()));
		  scrollPane.setBounds(37, 79, 449, 344);
		    TableColumn column = null;
		    for (int i = 0; i < 3; i++) {
		        column = table.getColumnModel().getColumn(i);
		        if (i == 2) {
		            column.setPreferredWidth(100); //sport column is bigger
		        } else {
		            column.setPreferredWidth(50);
		        }
		    }  

		this.add(scrollPane);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(37, 420, 115, 30);
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAll();
				add(new AdminHomepage());
			
				
			}
			
		});
		
		System.out.println("this is " + table.getColumnName(1));
		System.out.println("this is " + table.getWidth() + " " +  table.getHeight());
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public void getrows() {
		String specialty = "";
		int rating = 0;
		String SQL = "SELECT Specialty, AVG(Rating) AS AR FROM Doctor, Doctor_Rating WHERE Doctor.DocUsername = Doctor_Rating.DocUsername GROUP BY Specialty";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			ResultSet rs = stmt.executeQuery();
			rs.last();
			rows = rs.getRow();
			System.out.println(" there are " + rows +"rows ");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
		
	public void displayRating(){
		String specialty = "";
		int rating = 0;
		String SQL = "SELECT Specialty, AVG(Rating) AS AR FROM Doctor, Doctor_Rating WHERE Doctor.DocUsername = Doctor_Rating.DocUsername GROUP BY Specialty";
		
		Object[][] s = new Object[rows][2]; 
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			ResultSet rs = stmt.executeQuery();
	
			while(rs.next()){
				specialty = rs.getString("Specialty");
				rating = rs.getInt("AR");
				s[rs.getRow()-1][0]= new String(specialty);
			
				s[rs.getRow()-1][1] = new Integer(rating);
				
		

				
			}
		
			for(int x =0;x<s.length;x++){
				for(int y =0;y<s[x].length;y++){
					System.out.println(s[x][y]);
					data[x][y]= s[x][y];
				}
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
			rs.last();
			Object[][] s = new Object[rs.getRow()][2]; 
			for ( int i =0; i<s.length;i++){
				for ( int j =0; j<s[i].length;j++)
					s[i][j] = new String ("default");
			}
			rs.first();
			do{
				specialty = rs.getString("Specialty");
				count = rs.getInt("C");
				s[rs.getRow()-1][0]= new String(specialty);
				
				s[rs.getRow()-1][1] = new Integer(count);
				
			
				
			}while(rs.next());



		
			for(int x =0;x<data.length;x++){
				
				
					for(int y =0;y<s.length;y++)
					if(data[x][0].equals( s[y][0]))
					data[x][2]= s[y][1];
			
				}
			}
		}
}
