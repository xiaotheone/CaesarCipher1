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
import javax.swing.JComboBox;
import javax.swing.JTable;


public class PatientVisitReportPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTable table;
	
	
	public PatientVisitReportPanel(){
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		
		JButton btnGoBack = new JButton("GO BACK");
		btnGoBack.setBounds(29, 403, 95, 29);
		add(btnGoBack);
		
		JLabel lblNewLabel = new JLabel("Patient Visit Report");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(199, 39, 147, 24);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select Month");
		lblNewLabel_1.setBounds(110, 91, 87, 16);
		add(lblNewLabel_1);
		
		JComboBox comboMonth = new JComboBox();
		comboMonth.setBounds(209, 87, 72, 27);
		add(comboMonth);
		
		JComboBox comboYear = new JComboBox();
		comboYear.setBounds(294, 87, 72, 27);
		add(comboYear);
		
		table = new JTable();
		table.setBounds(51, 142, 446, 192);
		add(table);
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
