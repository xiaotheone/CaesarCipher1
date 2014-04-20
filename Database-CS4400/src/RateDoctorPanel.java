import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class RateDoctorPanel extends JPanel{
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JComboBox comboBox;
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rate1;
	private JRadioButton rate2;
	private JRadioButton rate3;
	private JRadioButton rate4;
	private JRadioButton rate5;
	private JButton btnBack;
	public RateDoctorPanel() throws SQLException {
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblRateADoctor = new JLabel("Rate A Doctor");
		lblRateADoctor.setBounds(215, 23, 126, 23);
		add(lblRateADoctor);
		
		JLabel lblSetlectDoctors = new JLabel("Setlect Doctors:");
		lblSetlectDoctors.setBounds(121, 108, 86, 33);
		add(lblSetlectDoctors);
		
		String[] docNames = doctorList();
	
		comboBox = new JComboBox(docNames);
		comboBox.setBounds(233, 105, 137, 38);
		add(comboBox);
		
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(121, 185, 86, 33);
		add(lblRating);
		
		 rate1 = new JRadioButton("1");
		rate1.setBounds(118, 208, 55, 33);
		add(rate1);
		
		 rate2 = new JRadioButton("2");
		rate2.setBounds(175, 208, 55, 33);
		add(rate2);
		
		 rate3 = new JRadioButton("3");
		rate3.setBounds(233, 208, 55, 33);
		add(rate3);
		
		 rate4 = new JRadioButton("4");
		rate4.setBounds(290, 208, 55, 33);
		add(rate4);
		
		 rate5 = new JRadioButton("5");
		rate5.setBounds(347, 208, 55, 33);
		add(rate5);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(377, 313, 109, 33);
		add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					rateDoctor();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	});
		
		bg.add(rate1);
		bg.add(rate2);
		bg.add(rate3);
		bg.add(rate4);
		bg.add(rate5);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(68, 313, 105, 28);
		add(btnBack);
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new PatientHomepagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
	
	public String[] doctorList() throws SQLException {
		List<String> nameList = new ArrayList<String>();
		String SQL = "SELECT Lname FROM Doctor, Visit WHERE Visit.DocUsername = Doctor.DocUsername	AND PatUsername =?";

		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				nameList.add("DR." + rs.getString("Lname"));

			}

			String[] docNames = (String[]) nameList.toArray(new String[nameList
					.size()]);
			for (int i = 0; i < docNames.length; i++) {
				System.out.println(docNames[i]);
			}
			return docNames;

		}

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
	public void rateDoctor() throws SQLException{
		String choice;
		String SQL1 = "SELECT * FROM Doctor_Rating WHERE DocUsername = ? AND PatientUsername = ?";
		try(PreparedStatement stmt = conn.prepareStatement(SQL1);){
			stmt.setString(1, getDoctorUsename((comboBox.getSelectedItem()).toString().substring(3)));
			stmt.setString(2, currentPatient.cp.getPatientUsername());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()){
				choice = "insert";
			}else {
				choice = "update";
			}
		}
		
		if (choice.equals("insert")) {
			String SQL = "INSERT INTO Doctor_Rating VALUES (?,?,?)";
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {

				stmt.setString(1, getDoctorUsename((comboBox.getSelectedItem())
						.toString().substring(3)));
				stmt.setString(2, currentPatient.cp.getPatientUsername());
				if (rate1.isSelected())
					stmt.setInt(3, 1);
				else if (rate2.isSelected())
					stmt.setInt(3, 2);
				else if (rate3.isSelected())
					stmt.setInt(3, 3);
				else if (rate4.isSelected())
					stmt.setInt(3, 4);
				else if (rate5.isSelected())
					stmt.setInt(3, 5);

				int affected = stmt.executeUpdate();
				if (affected == 1) {
					System.out.println("rated");
					JOptionPane.showMessageDialog(getParent(), "rated");

					
				} else {
					System.err.println("error");
					JOptionPane.showMessageDialog(getParent(), "error");
				
				}
			}
		}else if (choice.equals("update")){
			String SQL = "UPDATE Doctor_Rating SET Rating =? WHERE DocUsername =? AND PatientUsername = ?";
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {

				stmt.setString(2, getDoctorUsename((comboBox.getSelectedItem())
						.toString().substring(3)));
				stmt.setString(3, currentPatient.cp.getPatientUsername());
				if (rate1.isSelected())
					stmt.setInt(1, 1);
				else if (rate2.isSelected())
					stmt.setInt(1, 2);
				else if (rate3.isSelected())
					stmt.setInt(1, 3);
				else if (rate4.isSelected())
					stmt.setInt(1, 4);
				else if (rate5.isSelected())
					stmt.setInt(1, 5);

				int affected = stmt.executeUpdate();
				if (affected == 1) {
					System.out.println("rated");
					JOptionPane.showMessageDialog(getParent(), "rated");

				
				} else {
					System.err.println("error");
					JOptionPane.showMessageDialog(getParent(), "error");
					
				}
			}
			
		}
		
	}
}