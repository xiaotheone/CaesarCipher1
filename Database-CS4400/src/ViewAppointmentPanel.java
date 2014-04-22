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

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class ViewAppointmentPanel extends JPanel{
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private String date;
	private JTextArea textArea;
	private JComboBox comboDay;
	private JComboBox comboMonth;
	private JComboBox comboYear;
	
	public ViewAppointmentPanel(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblAppointmentsCalendar = new JLabel("Appointments Calendar");
		lblAppointmentsCalendar.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblAppointmentsCalendar.setBounds(167, 23, 182, 31);
		add(lblAppointmentsCalendar);
		
		JLabel lblSelcetDate = new JLabel("Selcet Date:");
		lblSelcetDate.setBounds(75, 97, 84, 24);
		add(lblSelcetDate);
		
		String[] day = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
						"21","22","23","24","25","26","27","28","29","30","31"};
		comboDay = new JComboBox(day);
		comboDay.setBounds(156, 97, 63, 27);
		add(comboDay);
		
		String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		comboMonth = new JComboBox(month);
		comboMonth.setBounds(231, 97, 118, 27);
		add(comboMonth);
		
		String[] year = {"2012", "2013", "2014", "2015", "2016"};
		comboYear = new JComboBox(year);
		comboYear.setBounds(363, 97, 72, 27);
		add(comboYear);
		
		
		String[] columnName = {"Sno", "Patient Name", "Scheduled Time"};
		int numRows = 5 ;
		DefaultTableModel model = new DefaultTableModel(numRows, columnName.length) ;
		model.setColumnIdentifiers(columnName);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(444, 96, 93, 29);
		add(btnSearch);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(87, 170, 369, 170);
		add(textArea);
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					getAppment();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1);
				}
			}
		});
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(75, 371, 100, 29);
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
					System.err.println(e1);
				}
			}
		});
		
		
		try {
			viewCurrentDate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.err.println(e1);
		}
	}
	
	public void viewCurrentDate() throws SQLException{
		
		String SQL = "SELECT PatientUsername , Time FROM Appointments WHERE	DocUsername = ? AND Date = ?";
		ResultSet rs = null;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date).toString();

		
		try (PreparedStatement stmt = conn.prepareStatement(SQL)){
			
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt.setString(2, currentDate);
			rs = stmt.executeQuery();			
			
			if (!rs.next()){
				textArea.append("No Appointment today!");
			}
			while(rs.next()) {
				
				textArea.append("Patient Name: " + rs.getString("PatientUsername") +" || " + "Scheduled Time: " + rs.getString("Time") +"\n");
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}
	
	public boolean getAppment() throws SQLException{
		
		String SQL = "SELECT PatientUsername , Time FROM Appointments WHERE	DocUsername = ? AND Date = ?";
		ResultSet rs = null;
		date = comboYear.getSelectedItem().toString() +"-"+ comboMonth.getSelectedItem().toString() + "-" + comboDay.getSelectedItem().toString();

		
		try (PreparedStatement stmt = conn.prepareStatement(SQL)){
			
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			stmt.setString(2, date);
			
			rs = stmt.executeQuery();
						
			while(rs.next()) {
				
				textArea.append("Patient Name: " + rs.getString("PatientUsername") +" || " + "Scheduled Time: " + rs.getString("Time") +"\n");
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
		return true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
