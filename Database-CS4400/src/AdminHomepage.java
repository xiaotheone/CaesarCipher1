import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The class contains the home page of administrative
 * @author bochen
 *
 */
public class AdminHomepage extends JPanel{
	
	public static BufferedImage image;


	public AdminHomepage(){		
		
		System.out.println("into admin page");

		setSize(550, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Homepage");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(221, 29, 85, 32);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Billing");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new CreateBillPanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(new Color(255, 248, 220));
		btnNewButton.setBounds(183, 105, 140, 45);
		add(btnNewButton);
		
		JButton btnDoctorPerformReport = new JButton("Doctor Perform Report");
		btnDoctorPerformReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				try {
					add(new DoctorPerformanceReportPanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDoctorPerformReport.setBackground(new Color(255, 245, 238));
		btnDoctorPerformReport.setBounds(183, 171, 140, 45);
		add(btnDoctorPerformReport);
		
		JButton btnSurgeryReport = new JButton("Surgery Report");
		btnSurgeryReport.setBackground(new Color(255, 245, 238));
		btnSurgeryReport.setBounds(183, 228, 140, 45);
		add(btnSurgeryReport);
		
		JButton btnPatientVisit = new JButton("Patient Visit Report");
		btnPatientVisit.setBackground(new Color(255, 245, 238));
		btnPatientVisit.setBounds(183, 285, 140, 45);
		add(btnPatientVisit);
		
		JButton btnNewButton_1 = new JButton("Close");
		btnNewButton_1.setBounds(405, 386, 117, 29);
		add(btnNewButton_1);
		
		
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
}
