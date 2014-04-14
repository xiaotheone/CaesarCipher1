import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private JTextField UsernameField;
	private JTextField PasswordField;
	public static BufferedImage image;
	private JTextField UserField2;
	private JTextField PassworField2;
	private JTextField confirmField_2;

	public LoginPanel() {

		setSize(550, 450);
		setLayout(null);

		setBackground(Color.WHITE);

		try {
			image = ImageIO.read(new File("Images/buzz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(99, 118, 72, 25);
		add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(99, 171, 72, 25);
		add(lblPassword);

		UsernameField = new JTextField();
		UsernameField.setBounds(204, 116, 134, 28);
		add(UsernameField);
		UsernameField.setColumns(10);

		PasswordField = new JTextField();
		PasswordField.setColumns(10);
		PasswordField.setBounds(204, 169, 134, 28);
		add(PasswordField);

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBounds(299, 208, 117, 29);
		add(btnNewButton);

		JButton btnNew = new JButton("Create Account");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				RegisterPanel();
			}
		});
		btnNew.setBounds(96, 208, 125, 29);
		add(btnNew);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(246, 19, 35, 16);
		add(lblLogin);
		repaint();
		setVisible(true);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public void RegisterPanel() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("New User Registration");
		lblNewLabel.setBounds(199, 21, 145, 33);
		add(lblNewLabel);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(41, 130, 69, 16);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(41, 163, 69, 16);
		add(lblPassword);

		JLabel lblConfirm = new JLabel("Confirm Password");
		lblConfirm.setBounds(41, 191, 122, 16);
		add(lblConfirm);

		JLabel lblType = new JLabel("Type of User");
		lblType.setBounds(41, 221, 84, 16);
		add(lblType);

		UserField2 = new JTextField();
		UserField2.setBounds(164, 126, 200, 23);
		add(UserField2);
		UserField2.setColumns(10);

		PassworField2 = new JTextField();
		PassworField2.setColumns(10);
		PassworField2.setBounds(164, 156, 200, 23);
		add(PassworField2);

		confirmField_2 = new JTextField();
		confirmField_2.setColumns(10);
		confirmField_2.setBounds(164, 185, 200, 23);
		add(confirmField_2);
		String[] userType = { "Administrator", "Doctor", "Patient" };
		@SuppressWarnings("unchecked")
		JComboBox comboType = new JComboBox(userType);
		comboType.setBackground(UIManager.getColor("CheckBox.background"));
		comboType.setForeground(Color.BLACK);
		comboType.setBounds(164, 217, 200, 27);
		add(comboType);

		JButton RegisterButton = new JButton("Register");
		RegisterButton.setBounds(382, 251, 117, 29);
		add(RegisterButton);

	}
	
	
	public static void checkData(ResultSet rs) throws SQLException{
		int count = 0;
		while(rs.next()){
			String userName = rs.getString("UserName");
			
			if (userName.equals("bchen80")){
				System.out.println("Welcome, Bo Chen!");
			}
			
			count += 1;
		}
		System.out.println("Total user number: " + count);
	}
	
	
}
