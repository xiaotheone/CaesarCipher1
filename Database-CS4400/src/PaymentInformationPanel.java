import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PaymentInformationPanel extends JPanel{
	
	
	private int newPaymentInfo;
	public  ArrayList visitID = new ArrayList();
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private JTextField tfName;
	private JTextField tfCardNo;
	private JTextField tfCVV;
	private String cardNum;
	private JComboBox CbType;
	protected ArrayList MedicineName = new ArrayList();
	private JTextField dateField;
	public PaymentInformationPanel() throws ParseException, SQLException {
		setSize(550, 450);
		setLayout(null);
		
		JLabel lblPaymentInformation = new JLabel("Payment Information");
		lblPaymentInformation.setBounds(193, 40, 131, 16);
		add(lblPaymentInformation);
		
		JLabel lblCardHoldersName = new JLabel("Card Holder's Name:");
		lblCardHoldersName.setBounds(89, 139, 143, 16);
		add(lblCardHoldersName);
		
		JLabel lblCardNumber = new JLabel("Card Number:");
		lblCardNumber.setBounds(89, 194, 125, 16);
		add(lblCardNumber);
		

		JLabel lblTypeOfCard = new JLabel("Type of Card:");
		lblTypeOfCard.setBounds(89, 255, 125, 16);
		add(lblTypeOfCard);
		
		JLabel lblCvv = new JLabel("CVV:");
		lblCvv.setBounds(89, 305, 61, 16);
		add(lblCvv);
		
		JLabel lblDateOfExpiry = new JLabel("Date of Expiry:");
		lblDateOfExpiry.setBounds(89, 354, 110, 16);
		add(lblDateOfExpiry);
		
		tfName = new JTextField();
		tfName.setBounds(292, 133, 134, 28);
		add(tfName);
		tfName.setColumns(10);
		
		tfCardNo = new JTextField();
		tfCardNo.setBounds(292, 188, 134, 28);
		add(tfCardNo);
		tfCardNo.setColumns(10);
		
		String[] type = {"Visa", "American Express", "Discover", "Master"};
	    CbType = new JComboBox(type);
		CbType.setBounds(292, 251, 110, 27);
		add(CbType);
		
		tfCVV = new JTextField();
		tfCVV.setBounds(292, 299, 134, 28);
		add(tfCVV);
		tfCVV.setColumns(10);
		

		JButton btnOrder = new JButton("Order");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					order();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnOrder.setBounds(389, 399, 117, 29);
		add(btnOrder);
		
		dateField = new JTextField();
		dateField.setBounds(292, 352, 134, 28);
		dateField.setToolTipText("format:1990-01-01");
		add(dateField);
		dateField.setColumns(10);
		
		JButton back = new JButton("Back");
		back.setBounds(38, 397, 112, 28);
		add(back);	

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					removeAll();
					add(new OrderMedicationPanel());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton save = new JButton("save payment method");
		save.setBounds(355, 94, 169, 28);
		add(save);

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(newPaymentInfo==1)
					savePaymentInfo();
					else{
						JOptionPane.showMessageDialog(getParent(), "you aready have a payment method ");
					}
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
		
		
		hasPaymentInfo();
		populatePaymentInfo();

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public boolean hasPaymentInfo() throws SQLException {
		String SQL = "SELECT CardNumber, COUNT(CardNumber) FROM Patient WHERE PatientUsername=?";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			
			ResultSet rs = stmt.executeQuery();
			String s="initial";
			int i =5;
			if (rs.next()) {
				
			     s = rs.getString("CardNumber");
				if (s==null) {
					System.out.println("no payment info found");
					newPaymentInfo =1;
					return false;
				} else {
					System.out.println("payment info found");
					cardNum = s;
					newPaymentInfo =0;
					return true;
				}
			}
			return false;

		}
	}
	
	
	public void populatePaymentInfo() throws SQLException{
		if(hasPaymentInfo()){
			
			String SQL = "SELECT * FROM Payment_Information WHERE CardNumber=?";
			
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
				
				stmt.setString(1, cardNum);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					String cardHolder = rs.getString("Cardholdersname");
					tfName.setText(cardHolder);
					
					tfCardNo.setText(cardNum);
					CbType.setSelectedItem(rs.getString("Type"));
					dateField.setText(rs.getString("DateOfExpiry"));
					
					}
				}
		}
	}
	
	public void order() throws SQLException{
		
		if(this.newPaymentInfo==1){
			this.savePaymentInfo();
		}
		String SQL = "SELECT * FROM Payment_Information WHERE CardNumber=? AND Cardholdersname=? AND CVV=? AND DateOfExpiry=? AND Type=?";
		
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			
			stmt.setString(1, tfCardNo.getText());
			stmt.setString(2, tfName.getText());
			stmt.setString(3, tfCVV.getText());
			stmt.setString(4, dateField.getText());
			stmt.setString(5, CbType.getSelectedItem().toString());
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
					System.out.println("corret payment info");
					 updatePrescription();
					
					
				}
			}
			
	}

	public void updatePrescription() throws SQLException {
		for (int i = 0; i < visitID.size(); i++) {
			String SQL = "UPDATE Prescription SET Ordered = \"yes\" WHERE VisitID=? AND MedicineName =?";
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {

				stmt.setInt(1, (int) visitID.get(i));
				stmt.setString(2, (String) MedicineName.get(i));

				int affected = stmt.executeUpdate();

				if (affected == 1) {
					System.out.println("prescription ordered");
					JOptionPane.showMessageDialog(getParent(), "prescription ordered");

				} else {
					System.err.println("order error");

				}

			}
		}
	}

	public void savePaymentInfo() throws SQLException {
		if (newPaymentInfo == 1) {
			String SQL = "INSERT INTO Payment_Information VALUES(?,?,?,?,?)";
			try (PreparedStatement stmt = conn.prepareStatement(SQL);) {

				stmt.setString(1, tfCardNo.getText());
				stmt.setString(2, tfName.getText());
				stmt.setString(3, tfCVV.getText());
				stmt.setString(4, dateField.getText());
				stmt.setString(5, CbType.getSelectedItem().toString());
				int affected = stmt.executeUpdate();

				if (affected == 1) {
					System.out.println("Payment method saved");
					

				} else {
					System.err.println("save error");

				}
			}
			
			String SQL2 = "UPDATE Patient SET CardNumber =? WHERE PatientUsername =?";
			try (PreparedStatement stmt = conn.prepareStatement(SQL2);) {
				stmt.setString(1, tfCardNo.getText());
				stmt.setString(2, currentPatient.cp.getPatientUsername());
				int affected = stmt.executeUpdate();

				if (affected == 1) {
					System.out.println("card number method saved");
					JOptionPane.showMessageDialog(getParent(), "Payment method saved");
					newPaymentInfo =0;

				} else {
					System.err.println("save error");

				}
			}
		}
	}
}