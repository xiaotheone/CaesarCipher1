import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;


public class BillingPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField patientNameField;
	
	public BillingPanel(){
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("Billing");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(239, 44, 49, 29);
		add(lblNewLabel);
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(87, 100, 89, 16);
		add(lblPatientName);
		
		patientNameField = new JTextField();
		patientNameField.setBounds(188, 94, 134, 28);
		add(patientNameField);
		patientNameField.setColumns(10);
		
		JButton btnCreateBilling = new JButton("Create Bill");
		btnCreateBilling.setBounds(334, 95, 117, 29);
		add(btnCreateBilling);
		
		JComboBox comboPatient = new JComboBox();
		comboPatient.setBounds(57, 160, 187, 27);
		add(comboPatient);
		
		JLabel lblNewLabel_1 = new JLabel("Patient Name     Phone Number");
		lblNewLabel_1.setBounds(57, 132, 203, 16);
		add(lblNewLabel_1);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(334, 159, 117, 29);
		add(btnSelect);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(57, 214, 394, 145);
		add(textArea);
		
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(29, 403, 95, 29);
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
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
