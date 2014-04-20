import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientMessagePanel extends JPanel{
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public PatientMessagePanel() throws SQLException {
		setSize(550, 450);
		setLayout(null);

		setSize(550, 450);
		setLayout(null);
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		

		JLabel lblMessages = new JLabel("Messages");
		lblMessages.setBounds(165, 18, 61, 16);
		add(lblMessages);
		
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(17, 102, 47, 16);
		add(lblStatus);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(86, 102, 48, 16);
		add(lblDate);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(165, 102, 61, 16);
		add(lblFrom);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(244, 102, 61, 16);
		add(lblMessage);
		
		JLabel lblStatus_1_1 = new JLabel("Status1");
		lblStatus_1_1.setBounds(17, 143, 47, 16);
		add(lblStatus_1_1);
		
		JLabel lblStatus_1 = new JLabel("Status2");
		lblStatus_1.setBounds(17, 186, 61, 16);
		add(lblStatus_1);
		
		JLabel lblStatus_2 = new JLabel("Status3");
		lblStatus_2.setBounds(17, 231, 61, 16);
		add(lblStatus_2);
		
		JLabel lblDate_1 = new JLabel("Date1");
		lblDate_1.setBounds(86, 143, 61, 16);
		add(lblDate_1);
		
		JLabel lblDate_2 = new JLabel("Date2");
		lblDate_2.setBounds(86, 186, 61, 16);
		add(lblDate_2);
		
		JLabel lblDate_3 = new JLabel("Date3");
		lblDate_3.setBounds(86, 231, 61, 16);
		add(lblDate_3);
		
		JLabel lblSender = new JLabel("Sender1");
		lblSender.setBounds(165, 143, 61, 16);
		add(lblSender);
		
		JLabel lblSender_1 = new JLabel("Sender2");
		lblSender_1.setBounds(165, 186, 61, 16);
		add(lblSender_1);
		
		JLabel lblSender_2 = new JLabel("Sender3");
		lblSender_2.setBounds(165, 231, 61, 16);
		add(lblSender_2);
		
		JLabel lblMessage_1 = new JLabel("Message1");
		lblMessage_1.setBounds(244, 143, 200, 16);
		add(lblMessage_1);
		
		JLabel lblMessage_2 = new JLabel("Message2");
		lblMessage_2.setBounds(244, 186, 200, 16);
		add(lblMessage_2);
		
		JLabel lblMessage_3 = new JLabel("Message3");
		lblMessage_3.setBounds(244, 231, 200, 16);
		add(lblMessage_3);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeAll();
				try {
					add(new PatientHomepagePanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		lblBack.setBounds(383, 259, 61, 16);
		add(lblBack);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	

}
