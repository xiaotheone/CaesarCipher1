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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class VisitCalendar extends JPanel{
	private static Connection conn = ConnectionManager.getInstance()
			.getConnection();
	public static BufferedImage image;
	private JTable tableCal;
	private static DefaultTableModel mtblCalendar;
	private int realYear, realMonth, realDay, currentYear, currentMonth;
	private JComboBox comboMonth, comboYear;
	String[] months =  {"January", "February", "March", "April", "May", "June", 
			"July", "August", "September", "October", "November", "December"};
	String[] years = {"2011", "2012", "2013", "2014", "2015", "2016"};

	
	public VisitCalendar(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		setSize(550, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Appointment Calendar");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(184, 31, 165, 24);
		add(lblNewLabel);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setBounds(47, 71, 44, 16);
		add(lblMonth);
		

		comboMonth = new JComboBox(months);
		comboMonth.setBounds(103, 67, 109, 27);
		add(comboMonth);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(224, 72, 40, 16);
		add(lblYear);
		
		comboYear = new JComboBox(years);
		comboYear.setBounds(276, 68, 89, 27);
		add(comboYear);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(377, 67, 117, 29);
		add(btnSelect);
		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int month = comboMonth.getSelectedIndex();
				int year = Integer.parseInt(comboYear.getSelectedItem().toString());
				try {
					selectMonth(month, year);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.err.println(e1);
				}
			}
		});
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(6, 415, 117, 29);
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
		
		mtblCalendar = new DefaultTableModel();
		tableCal = new JTable(mtblCalendar);
		tableCal.setBounds(17, 119, 515, 293);
		add(tableCal);

		GregorianCalendar cal = new GregorianCalendar(); 
		realMonth = cal.get(GregorianCalendar.MONTH); 
		realYear = cal.get(GregorianCalendar.YEAR); 
		currentMonth = realMonth;
		currentYear = realYear;
		
		System.out.println("current:" + (currentMonth+1) + "-" + currentYear);
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

		for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
		
		
		tableCal.getParent().setBackground(tableCal.getBackground()); 
		
		tableCal.getTableHeader();
        tableCal.setRowHeight(40);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
        
        refreshCalendar(currentMonth, currentYear);
	}
	
	public void selectMonth(int month, int year) throws SQLException{
		String temp = "";
		List<String> list = new ArrayList<String>();

		if (month >= 9) {
			temp = year + "-" + (month+1) + "-";

		}else{
			temp = year + "-0" + (month+1) + "-";
			
		}
		ResultSet rs = null;
		ResultSet rs1 = null;

		
		String sql = "SELECT Date FROM Appointments WHERE DocUsername = ? AND Date LIKE '%" + temp + "%'";
		String sql1 = "SELECT COUNT(*) FROM Appointments WHERE DocUsername = ? AND Date LIKE ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmt1 = conn.prepareStatement(sql1)){
			
			stmt.setString(1, currentDoctor.cd.getDoctorUsername());
			rs = stmt.executeQuery();
			
			stmt1.setString(1, currentDoctor.cd.getDoctorUsername());
			
			while(rs.next()){
				stmt1.setString(2, rs.getString("Date"));
				rs1 = stmt1.executeQuery();
				
				if(rs1.next()){
					if(list.indexOf(rs.getString("Date") + "-" + rs1.getLong(1))  == -1)
						list.add(rs.getString("Date") + "-" + rs1.getLong(1));
				}
				
			}
			String[] temp2 = new String[list.size()];
			list.toArray(temp2);
			
		
			
			refreshCalendar(month, year, temp2);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		
	}
	
	private void refreshCalendar(int month, int year, String[] list) {
		// TODO Auto-generated method stub
		int nod, som;		
		int[] day = new int[100];
		int[] number = new int[100];
		List<Integer> dayList = new ArrayList<Integer>();
		List<Integer> numberList = new ArrayList<Integer>();
		
		for(int i = 0; i < list.length; i++){
			
			day[i] = Integer.parseInt(list[i].split("-")[2]);
			number[i] = Integer.parseInt(list[i].split("-")[3]);
			
			System.out.println(day[i] + "  " + number[i]);
		}
		
		
		
		 //Clear table
       for (int i=0; i<6; i++){
           for (int j=0; j<7; j++){
               mtblCalendar.setValueAt(null, i, j);
           }
       }
       
       GregorianCalendar cal = new GregorianCalendar(year, month, 1);
       nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
       som = cal.get(GregorianCalendar.DAY_OF_WEEK);
       
     //Draw calendar
       String newinput = "";
       for (int i=1; i<=nod; i++){
           int row = new Integer((i+som-2)/7);
           int column  =  (i+som-2)%7;
           
           for(int j = 0; j < day.length; j++){
        	   if(i == day[j]){
        		   newinput = i+ " " + "[" + number[j] + "]"; 
        		   mtblCalendar.setValueAt(newinput, row, column);
        		   System.out.println(newinput);
        	   }else{
        		   newinput = String.valueOf(i);
        	   }
        	   
           }
           
           if(mtblCalendar.getValueAt(row, column) == null){
        	   
        	   mtblCalendar.setValueAt(newinput, row, column);
           }
       }
	}

	private void refreshCalendar(int month, int year) {
		// TODO Auto-generated method stub
		int nod, som;
		
		 //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
      //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
        }
        
	}	
	
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
