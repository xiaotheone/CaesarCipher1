import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;


public class ViewAppointmentPanel extends JPanel{
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTable appointmentTable;

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
		JComboBox comboDay = new JComboBox(day);
		comboDay.setBounds(156, 97, 63, 27);
		add(comboDay);
		
		String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		JComboBox comboMonth = new JComboBox(month);
		comboMonth.setBounds(231, 97, 118, 27);
		add(comboMonth);
		
		String[] year = {"2012", "2013", "2014", "2015", "2016"};
		JComboBox comboYear = new JComboBox(year);
		comboYear.setBounds(363, 97, 72, 27);
		add(comboYear);
		
		
		
		String[] columnName = {"Sno", "Patient Name", "Scheduled Time"};
		int numRows = 5 ;
		DefaultTableModel model = new DefaultTableModel(numRows, columnName.length) ;
		model.setColumnIdentifiers(columnName);
		appointmentTable = new JTable( model);
		appointmentTable.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		appointmentTable.setBounds(75, 167, 360, 144);
		add(appointmentTable);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(420, 167, 15, 144);
		add(scrollBar);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
