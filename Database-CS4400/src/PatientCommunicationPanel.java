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
		setLayout(null);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		JLabel lblNewLabel = new JLabel("Select");
		lblNewLabel.setBounds(66, 54, 61, 16);
		add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(160, 50, 126, 27);
		add(comboBox);
		
		JLabel lblMessages = new JLabel("Message");
		lblMessages.setBounds(66, 100, 61, 16);
		add(lblMessages);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(170, 100, 220, 121);
		add(textArea);
		
		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.setBounds(327, 252, 117, 29);
		add(btnNewButton);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				add(new PatienProfilePanel());
				repaint();
			}
		});
		lblBack.setBounds(31, 265, 61, 16);
		add(lblBack);
		
		JLabel lblSendMessageTo = new JLabel("Send Message To Doctor");
		lblSendMessageTo.setBounds(123, 22, 163, 16);
		add(lblSendMessageTo);
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}	
}
	
	
