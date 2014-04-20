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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;


public class PatientVisitPanel extends JPanel{

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	public static BufferedImage image;
	private JTextField txtEnterName;
	private JTextField txtPhoneNumber;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField systolicField;
	private JTextField diastolicField;
	private JTable medicationTable;

	public PatientVisitPanel(){
		
		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPatientVistHistory = new JLabel("Patient Vist History");
		lblPatientVistHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPatientVistHistory.setBounds(193, 30, 148, 29);
		add(lblPatientVistHistory);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(26, 81, 45, 16);
		add(lblName);
		
		txtEnterName = new JTextField();
		txtEnterName.setBounds(71, 75, 134, 28);
		add(txtEnterName);
		txtEnterName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Phone:");
		lblNewLabel.setBounds(229, 81, 61, 16);
		add(lblNewLabel);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(277, 75, 134, 28);
		add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(423, 76, 95, 29);
		add(btnSearch);
		
		JLabel lblPatientName = new JLabel("Patient Name");
		lblPatientName.setBounds(6, 117, 95, 16);
		add(lblPatientName);
		
		JLabel lblPatientPhone = new JLabel("Phone Number");
		lblPatientPhone.setBounds(110, 117, 95, 16);
		add(lblPatientPhone);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(6, 145, 88, 28);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(110, 145, 95, 28);
		add(textField_1);
		
		JButton btnView = new JButton("View");
		btnView.setBounds(210, 146, 75, 29);
		add(btnView);
		
		JButton btnRecordAVisit = new JButton("Record a Visit");
		btnRecordAVisit.setBounds(297, 146, 114, 29);
		add(btnRecordAVisit);
		
		JPanel DateofVisitPanel = new JPanel();
		DateofVisitPanel.setBounds(6, 222, 140, 200);
		add(DateofVisitPanel);
		
		JLabel lblDateOfVisit = new JLabel("Date of Visit");
		
		JTextArea VisitDateArea = new JTextArea();
		VisitDateArea.setEditable(false);
		GroupLayout gl_DateofVisitPanel = new GroupLayout(DateofVisitPanel);
		gl_DateofVisitPanel.setHorizontalGroup(
			gl_DateofVisitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DateofVisitPanel.createSequentialGroup()
					.addGroup(gl_DateofVisitPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_DateofVisitPanel.createSequentialGroup()
							.addGap(33)
							.addComponent(lblDateOfVisit))
						.addGroup(gl_DateofVisitPanel.createSequentialGroup()
							.addGap(19)
							.addComponent(VisitDateArea, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_DateofVisitPanel.setVerticalGroup(
			gl_DateofVisitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DateofVisitPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDateOfVisit)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(VisitDateArea, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		DateofVisitPanel.setLayout(gl_DateofVisitPanel);
		
		JLabel lblDateOfVisit_1 = new JLabel("Date of Visit");
		lblDateOfVisit_1.setBounds(158, 207, 82, 16);
		add(lblDateOfVisit_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(277, 201, 134, 28);
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure");
		lblBloodPressure.setBounds(158, 255, 95, 16);
		add(lblBloodPressure);
		
		systolicField = new JTextField();
		systolicField.setEditable(false);
		systolicField.setBounds(322, 249, 54, 28);
		add(systolicField);
		systolicField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Systolic:");
		lblNewLabel_1.setBounds(265, 255, 61, 16);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Diastolic:");
		lblNewLabel_2.setBounds(388, 255, 69, 16);
		add(lblNewLabel_2);
		
		diastolicField = new JTextField();
		diastolicField.setEditable(false);
		diastolicField.setColumns(10);
		diastolicField.setBounds(451, 249, 54, 28);
		add(diastolicField);
		
		JLabel lblNewLabel_3 = new JLabel("Diagnosis:");
		lblNewLabel_3.setBounds(158, 298, 82, 16);
		add(lblNewLabel_3);
		
		JTextArea diagnosisArea = new JTextArea();
		diagnosisArea.setEditable(false);
		diagnosisArea.setBounds(252, 298, 124, 29);
		add(diagnosisArea);
		
		JLabel lblNewLabel_4 = new JLabel("Medication Prescrebed");
		lblNewLabel_4.setBounds(158, 337, 75, 28);
		add(lblNewLabel_4);
		
		
		String[] tableColumn = {"Medicine Name", "Dosage", "Duration", "Notes"};
		int numRows = 5 ;
		DefaultTableModel model = new DefaultTableModel(numRows, tableColumn.length) ;
		model.setColumnIdentifiers(tableColumn);
		medicationTable = new JTable(model);
		medicationTable.setBounds(252, 333, 266, 89);
		add(medicationTable);
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
}
