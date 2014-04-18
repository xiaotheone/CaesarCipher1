import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private static Connection conn = ConnectionManager.getInstance().getConnection();
	private int gf;
	private JTextField UsernameField;
	private JPasswordField PasswordField;
	public static BufferedImage image;
	private JTextField UserField2;
	private JPasswordField PassworField2;
	private JPasswordField confirmField_2;
	private String inputName;
	private String inputPass;

	public LoginPanel() {
		
		setSize(550, 450);
		setLayout(null);

		//setBackground(Color.WHITE);

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

		PasswordField = new JPasswordField();
		PasswordField.setColumns(10);
		PasswordField.setBounds(204, 169, 134, 28);
		add(PasswordField);

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBounds(299, 208, 117, 29);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputName = UsernameField.getText();
				inputPass = new String(PasswordField.getPassword());
				try {
					if (checkUser(inputName, inputPass)) {
						// change to different panel， get into create profile
						removeAll();
						//add(new PatientProfilePanel());
						int type = checkUserType(inputName);
						switch (type) {
						case 0:
							removeAll();
							add(new AdminHomepage());
							break;
						case 1:
							removeAll();
							add(new PatientHomepagePanel());
							break;
						case 2:
							removeAll();
							add(new DoctorHomePage());
							break;

						}
					}					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void RegisterPanel(){
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

		PassworField2 = new JPasswordField();
		PassworField2.setColumns(10);
		PassworField2.setBounds(164, 156, 200, 23);
		add(PassworField2);

		confirmField_2 = new JPasswordField();
		confirmField_2.setColumns(10);
		confirmField_2.setBounds(164, 185, 200, 23);
		add(confirmField_2);
		String[] userType = { "Administrator", "Doctor", "Patient" };
		final JComboBox comboType = new JComboBox(userType);
		comboType.setToolTipText("Select user type");
		comboType.setVisible(true);
		comboType.setBackground(Color.WHITE);
		comboType.setForeground(Color.BLACK);
		comboType.setBounds(164, 217, 200, 27);
		add(comboType);

		JButton registerButton = new JButton("Register");
		registerButton.setBounds(382, 251, 117, 29);
		add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = UserField2.getText();
				String firstPassword = new String(PassworField2.getPassword());
				String confirmPassword = new String(confirmField_2.getPassword());
				int userType = comboType.getSelectedIndex();
				
				//compare two input password, if same go ahead create account
				if (firstPassword.equals(confirmPassword)) {
					
					try {
						if (userRegistration(newName, firstPassword)) {
							System.out.println("Successfule create user, going to profile page");
							
							if (userType == 1) {
								//swith to doctor profile creation
								
								removeAll();
								add(new DoctorProfilePanel());
								repaint();
							}else if (userType == 2) {
								// swith to patient profile creation
								
								removeAll();
								add(new PatientProfilePanel());
								repaint();
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(getParent(), "password not match, try again");
				}
			}
		});	
		
		JButton backButton = new JButton("Go Back");
		backButton.setBounds(25, 251, 117, 29);
		add(backButton);
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				add(new LoginPanel());
				
			}
		});
		
	}

	/*
	 * select a profile panel. 
	 */
	public int checkUserType(String username) throws SQLException{
		String sql1 = "SELECT * FROM Patient WHERE 'Patient Username' = ?";
		String sql2 = "SELECT * FROM Doctor WHERE 'Doc Username' = ?";
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try (
				PreparedStatement stmt1 = conn.prepareStatement(sql1);
				PreparedStatement stmt2 = conn.prepareStatement(sql2);) {

			// if the username is on patient table, return 1, doctor return 2
			stmt1.setString(1, username);
			stmt2.setString(1, username);
			rs1 = stmt1.executeQuery();
			rs2 = stmt2.executeQuery();

			if (rs1.next()){
				System.out.println("this a patient");

				return 1;
			}else if (rs2.next()) {
				System.out.println("this a doctor");
				return 2;
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * this method is checking the user's input username and password
	 * by using SQL
	 * @param username	username input
	 * @param password	password input
	 * @return true if user input right account info else return false
	 * @throws SQLException
	 */
	public boolean checkUser(String username, String password)
			throws SQLException {

		String sql = "SELECT * FROM User WHERE Username = ? ";
		ResultSet rs = null;

		try (

		PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setString(1, username);
			rs = stmt.executeQuery();

			if (rs.next()) {

				String tempPassword = rs.getString("Password");
				if (tempPassword.equals(password)) {
					
					JOptionPane.showMessageDialog(getParent(), "Welcome to GTMR");

					return true;
				} else {
					JOptionPane.showMessageDialog(getParent(), "Wrong password, please try again");
				}
			} else {
				JOptionPane.showMessageDialog(getParent(), "Cannot find username, Please try again");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return false;
	}
	
	/**
	 * The method is helping user to register a new account
	 * @param newName	 input username
	 * @param password	 input password
	 */
	protected boolean userRegistration(String newName, String password) throws SQLException{
		String SQL = "INSERT into User VALUES (?, ?)";
		ResultSet rs = null;
		
		try(PreparedStatement stmt = conn.prepareStatement(SQL);) {
			
			//setting variables
			stmt.setString(1, newName);
			stmt.setString(2, password);
			
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				System.out.println("data successful import");
				return true;
			} else {
				System.err.println("no row affected!!");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(e);
			return false;
		}
		
		
	}
	
}
