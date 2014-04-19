import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
@SuppressWarnings("serial")
public class PatientCommunicationPanel extends JPanel{
	
	
	public static BufferedImage image;
	
	public PatientCommunicationPanel() {
		
		setSize(550, 450);
		setLayout(null);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		JLabel lblNewLabel = new JLabel("Select");
		lblNewLabel.setBounds(66, 114, 61, 16);
		add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(170, 110, 126, 27);
		add(comboBox);
		
		JLabel lblMessages = new JLabel("Message");
		lblMessages.setBounds(66, 154, 61, 16);
		add(lblMessages);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(170, 154, 266, 200);
		add(textArea);
		
		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.setBounds(405, 390, 117, 29);
		add(btnNewButton);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				add(new PatientHomepagePanel());
				repaint();
			}
		});
		lblBack.setBounds(66, 395, 61, 16);
		add(lblBack);
		
		JLabel lblSendMessageTo = new JLabel("Send Message To Doctor");
		lblSendMessageTo.setBounds(170, 22, 163, 16);
		add(lblSendMessageTo);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	/*
	 * select names of all doctors
	 */
	public String[] doctorList(){
		
		return null;
		
	}
}
	
	
