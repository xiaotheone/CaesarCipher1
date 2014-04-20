import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
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
	
	public static BufferedImage image;
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private JTextField tfName;
	private JTextField tfCardNo;
	private JTextField tfCVV;

	public PaymentInformationPanel() throws ParseException {
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
		
		String[] type = {"Bank of America", "American Express", "Discover", "Chase"};
		JComboBox CbType = new JComboBox(type);
		CbType.setBounds(292, 251, 90, 27);
		add(CbType);
		
		tfCVV = new JTextField();
		tfCVV.setBounds(292, 299, 134, 28);
		add(tfCVV);
		tfCVV.setColumns(10);
		
		String[] dates = getDates();
		JComboBox CbDates = new JComboBox(dates);
		CbDates.setBounds(292, 350, 110, 27);
		add(CbDates);
		
		JButton btnOrder = new JButton("Order");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOrder.setBounds(389, 399, 117, 29);
		add(btnOrder);
		
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
	
	public String[] getDates() throws ParseException{
		List<String> datesList = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int count = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		while(count > 365){
			datesList.add(dateFormat.format(cal.getTime()));
			System.out.println(dateFormat.format(cal.getTime()));
			cal.add(Calendar.DATE, 1);
			count ++;
		}
		String[] dates = (String[]) datesList.toArray(new String[datesList.size()]);
		
		return dates;

	}
}
