import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * The mothod contains the home page of doctor
 * including all the function doctor can use
 * @author bochen
 *
 */
public class DoctorHomePage extends JPanel{
	
	public static BufferedImage image;

	
	public DoctorHomePage(){
		
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Homepage for Doctor");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(176, 30, 173, 34);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("View Appointment");
		btnNewButton.setBounds(58, 86, 130, 45);
		add(btnNewButton);
		
		JButton btnPatientVisit = new JButton("Patient Visit");
		btnPatientVisit.setBounds(58, 148, 130, 45);
		add(btnPatientVisit);
		
		JButton btnRecord = new JButton("Record a surgery");
		btnRecord.setBounds(58, 205, 130, 45);
		add(btnRecord);
		
		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCommunicate.setBounds(58, 259, 130, 45);
		add(btnCommunicate);
		
		JButton btnEditeProfile = new JButton("Edite Profile");
		btnEditeProfile.setBounds(58, 316, 130, 44);
		add(btnEditeProfile);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(377, 373, 117, 29);
		add(btnClose);
		
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
