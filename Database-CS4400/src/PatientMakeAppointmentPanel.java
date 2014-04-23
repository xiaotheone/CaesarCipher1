import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientMakeAppointmentPanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	JComboBox CbSpecialty;
	private String stime;
	private String  sweekday;
	private String sdate;
	private String sdoctorname;
	private Object[][] data;
	private int rows=0;
	private JTable table = new JTable();
	private JScrollPane scrollPane = new JScrollPane();
	private String[] columnNames = new String[5];
	
	private ArrayList doctorList = new ArrayList();
	public PatientMakeAppointmentPanel() {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblScheduleAppointmentsWith = new JLabel("Schedule Appointments With Doctors");
		lblScheduleAppointmentsWith.setBounds(127, 33, 280, 16);
		add(lblScheduleAppointmentsWith);
		
		JLabel lblSpecialty = new JLabel("Specialty:");
		lblSpecialty.setBounds(60, 84, 61, 16);
		add(lblSpecialty);
		
		String[] specialty = { "General Physician", "Heart Specialist", "Eye physician", "Orthopedics", "Psychiatry", "Gynecologist"};
		CbSpecialty = new JComboBox(specialty);
		CbSpecialty.setBounds(143, 80, 115, 27);
		add(CbSpecialty);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					remove(scrollPane);
			
					displayDoctorInfo();
					revalidate();
					repaint();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(334, 79, 117, 29);
		add(btnSearch);
		
		JButton btnRequestAppointment = new JButton("Request Appointment");
		btnRequestAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stime =table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString(); 
				
				stime =stime.substring(stime.indexOf(":")+2,stime.indexOf(":")+ 10);
				System.out.println("xx" + stime);
				
				 sweekday = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString(); 
				sweekday =sweekday.substring(0,sweekday.indexOf("y")+1);
				System.out.println("xx" + sweekday);
			   sdoctorname = table.getValueAt(table.getSelectedRow(), 0).toString(); 
			  sdate =  getNextWeekday(sweekday);
			  System.out.println("xx" + sdate);
				try {
					sdoctorname = getDoctorUsename(sdoctorname.split(" ")[2]);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("xxzz" + sdoctorname);
				
				
				
				try {
					if(checkDayAva()){
						String SQL = "INSERT INTO Appointments VALUES (?,?,?,?)";
						try (PreparedStatement stmt = conn.prepareStatement(SQL);) {

							stmt.setString(1,sdoctorname);
								
							stmt.setString(2, currentPatient.cp.getPatientUsername());
							stmt.setString(3, sdate);
							stmt.setString(4, stime);
							int affected = stmt.executeUpdate();
							if (affected == 1) {
								System.out.println("rated");
								JOptionPane.showMessageDialog(getParent(), "successfully make the appointment on next "+ sweekday  +" in "+sdate + " " + stime);

								
							} else {
								System.err.println("error");
								JOptionPane.showMessageDialog(getParent(), "error");
							
							}
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRequestAppointment.setBounds(208, 394, 160, 29);
		add(btnRequestAppointment);
		
		JButton btnBack = new JButton("Back");
		
		btnBack.setBounds(23, 394, 81, 29);
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new PatientHomepagePanel());
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
			}
		});
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public int getrows() throws SQLException{
		int i=0;
		String SQL ="SELECT	D.Fname, D.Lname, D.WorkPhone, D.RoomNo, T.From, T.To, T.Day "
	+	"FROM		Doctor AS D, Doctor_Availability AS T "
	+	"WHERE	D.DocUsername = T.docUsername AND D.Specialty = ? ";

	
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, CbSpecialty.getSelectedItem().toString());
			rs = stmt.executeQuery();
		
				rs.last();
				i= rs.getRow();
			
			
		}
		return i; 
	}
	public void displayDoctorInfo() throws SQLException{
		addTable();
		String SQL ="SELECT	D.Fname, D.Lname, D.WorkPhone, D.RoomNo, T.From, T.To, T.Day "
	+	"FROM		Doctor AS D, Doctor_Availability AS T "
	+	"WHERE	D.DocUsername = T.docUsername AND D.Specialty = ? ";

		String DoctorName = "";
		String PhoneNumber = "";
		String RoomNumber = "";
		String Aval ="";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, CbSpecialty.getSelectedItem().toString());
			rs = stmt.executeQuery();
			while(rs.next()){
				DoctorName = "DR. " + rs.getString("Fname") + " " + rs.getString("Lname");
				data[rs.getRow()-1][0] = new String(DoctorName);
				
				PhoneNumber = rs.getString("WorkPhone");
				
				data[rs.getRow()-1][1] = new String(PhoneNumber);
				
				RoomNumber = "#" +rs.getString("RoomNo");
				
				data[rs.getRow()-1][2] = new String(RoomNumber);
				
				Aval = rs.getString("Day")+ ": " + rs.getString("From") +" - " + rs.getString("To");
				
				data[rs.getRow()-1][3] = new String(Aval);
				
				System.out.println(DoctorName + "     " + PhoneNumber + "      " + RoomNumber);
			}
		
		} getRating();
		
	}
	
	
	public void addTable() throws SQLException{

	    columnNames[0]= "Doctor Name";
	    columnNames[1]= "Phone #";
	    columnNames[2]= "Room #";
	    columnNames[3]= "Availability";
	    columnNames[4]= "Avarage Rating";
	    rows =getrows();
		System.out.println("xxxxx  " + rows);
		data = new Object[rows][5];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = new String("0");
				System.out.println("xxxxx  " + data[i][j]);
			}
		}
		table = new JTable(data, columnNames);

		table.setFillsViewportHeight(true);

		table.getTableHeader();
	
		table.setRowHeight(30);
		
		   scrollPane = new JScrollPane(table);
		  scrollPane.setPreferredSize(new Dimension(480,table.getHeight()));
		  scrollPane.setBounds(5, 129, 549, 264);
		    TableColumn column = null;
		    for (int i = 0; i < 5; i++) {
		        column = table.getColumnModel().getColumn(i);
		        if (i == 3) {
		            column.setPreferredWidth(125);
		        } else {
		            column.setPreferredWidth(60);
		        }
		    }  

		this.add(scrollPane);
	}
	public String getNextWeekday(String day){
		Calendar now = Calendar.getInstance();
		int weekday = now.get(Calendar.DAY_OF_WEEK);

		    // calculate how much to add
		    // the 2 is the difference between Saturday and Monday
			int offset = 0;
			if(day.equalsIgnoreCase("monday")){
				offset =2;
			}
			
			if(day.equalsIgnoreCase("tuesday")){
				offset =3;
			}
			if(day.equalsIgnoreCase("wednesday")){
				offset =4;
			}
			if(day.equalsIgnoreCase("thursday")){
				offset =5;
			}
			if(day.equalsIgnoreCase("friday")){
				offset =6;
			}

		    int days = (Calendar.SATURDAY - weekday + offset) % 7;
		    now.add(Calendar.DAY_OF_YEAR, days);
		
		
		// now is the date you want
		Date date2 = now.getTime();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(date2);
		System.out.println(format);
		
		return format;
	}
	public String getDoctorUsename(String Lname) throws SQLException{
		
		
		
		String SQL = "SELECT DocUsername FROM Doctor WHERE Lname = ?";
		String s="";
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, Lname);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			 s = rs.getString("DocUsername");
		}
		return s;
	}

	public boolean checkDayAva() throws SQLException {
		String s = "";
		String SQL = "SELECT	COUNT(*) FROM Appointments WHERE	DocUsername =? AND  Date = ? AND Time =?";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, sdoctorname);
			stmt.setString(2, sdate);
			stmt.setString(3, stime);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				s = rs.getString("COUNT(*)");

			}

			if (s.equalsIgnoreCase("0")) {
				return true;
			} else if (s.equalsIgnoreCase("1")) {
				return false;
			}

		}

		return false;
	}

	public void getRating() throws SQLException {
		String s ="";
		for (int i = 0; i < table.getRowCount(); i++) {
			String doctorname = table.getValueAt(i, 0).toString();
			doctorname = getDoctorUsename(doctorname.split(" ")[2]);
			
			String SQL = "SELECT AVG(Rating) AS AR FROM  Doctor_Rating WHERE DocUsername = ? GROUP BY DocUsername";
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
				stmt.setString(1, doctorname);

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					s = rs.getString("AR");

				}
				data[i][4] = new String (s);
			}
		}
	}


}
