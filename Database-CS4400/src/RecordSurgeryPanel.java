import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JComboBox;


public class RecordSurgeryPanel extends JPanel{
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField textField;
	private JTable patientTable;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	
	public RecordSurgeryPanel(){
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblSurgeryRecord = new JLabel("Surgery Record");
		lblSurgeryRecord.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblSurgeryRecord.setBounds(207, 32, 114, 29);
		add(lblSurgeryRecord);
		
		JLabel lblNewLabel = new JLabel("Search Patient:");
		lblNewLabel.setBounds(69, 75, 92, 16);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(170, 69, 134, 28);
		add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(314, 70, 117, 29);
		add(btnSearch);
		
		patientTable = new JTable();
		patientTable.setBounds(10, 102, 294, 50);
		add(patientTable);
		
		JLabel lblNewLabel_1 = new JLabel("Patient Name");
		lblNewLabel_1.setBounds(10, 195, 83, 16);
		add(lblNewLabel_1);
		
		JLabel lblSurgeryName = new JLabel("Surgery Name");
		lblSurgeryName.setBounds(10, 223, 92, 16);
		add(lblSurgeryName);
		
		JLabel lblProcedureName = new JLabel("Procedure Name");
		lblProcedureName.setBounds(10, 251, 107, 16);
		add(lblProcedureName);
		
		JLabel lblCptCode = new JLabel("CPT Code");
		lblCptCode.setBounds(10, 279, 61, 16);
		add(lblCptCode);
		
		JLabel lblNewLabel_2 = new JLabel("Number of Assistant");
		lblNewLabel_2.setBounds(10, 307, 107, 16);
		add(lblNewLabel_2);
		
		JLabel lblPreoperative = new JLabel("Pre-operative Medication");
		lblPreoperative.setBounds(6, 347, 96, 16);
		add(lblPreoperative);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(124, 347, 92, 38);
		add(textArea);
		
		textField_1 = new JTextField();
		textField_1.setBounds(114, 189, 114, 28);
		add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(114, 217, 114, 28);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(114, 273, 114, 28);
		add(textField_3);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(114, 247, 114, 27);
		add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(114, 303, 114, 27);
		add(comboBox_1);
		
		JLabel lblNewLabel_3 = new JLabel("Anesthesia Start Time");
		lblNewLabel_3.setBounds(256, 195, 122, 16);
		add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(390, 189, 83, 28);
		add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblSurgeryStartTime = new JLabel("Surgery Start time");
		lblSurgeryStartTime.setBounds(256, 223, 122, 16);
		add(lblSurgeryStartTime);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(390, 217, 83, 28);
		add(textField_5);
		
		JLabel lblSurgeryCompleteTime = new JLabel("Surgery Complete time");
		lblSurgeryCompleteTime.setBounds(256, 251, 122, 16);
		add(lblSurgeryCompleteTime);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(390, 245, 83, 28);
		add(textField_6);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(256, 307, 61, 16);
		add(lblDescription);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(337, 303, 136, 82);
		add(textArea_1);
		
		JButton btnRecord = new JButton("Record");
		btnRecord.setBounds(204, 398, 117, 29);
		add(btnRecord);
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
