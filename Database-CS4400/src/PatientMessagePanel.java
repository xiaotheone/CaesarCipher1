import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientMessagePanel extends JPanel{
	private JLabel s1 =new JLabel();
	private JLabel s2 =new JLabel();
	private JLabel s3=new JLabel();
	
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
		lblDate.setBounds(108, 102, 48, 16);
		add(lblDate);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(221, 102, 61, 16);
		add(lblFrom);
		
		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(333, 102, 61, 16);
		add(lblMessage);
		
		
		
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
		
		
		String[] status = getStatus();
		System.out.println( "hhhhhh" + getStatus().length);
		if(status.length > 0){
			
			s1.setBounds(17, 143, 47, 16);
			add(s1);
			s1.setText(this.getStatus()[0]);
			
			JLabel d1 = new JLabel("Date1");
			d1.setBounds(88, 143, 113, 16);
			add(d1);
			d1.setText(this.getTime()[0]);
			
			JLabel f1 = new JLabel("Sender1");
			f1.setBounds(221, 143, 61, 16);
			add(f1);
			f1.setText(this.getSender()[0]);
			
			
			JLabel m1 = new JLabel("Message1");
			m1.setBounds(300, 143, 200, 16);
			add(m1);
			m1.setText(this.getContent()[0]);
		}
		
		if(status.length > 1) {
			
			s2.setBounds(17, 186, 61, 16);
			add(s2);
			s2.setText(this.getStatus()[1]);
			
			
			JLabel d2 = new JLabel("Date2");
			d2.setBounds(88, 186, 113, 16);
			add(d2);
			d2.setText(this.getTime()[1]);
			
			
			JLabel f2 = new JLabel("Sender2");
			f2.setBounds(221, 186, 61, 16);
			add(f2);
			f2.setText(this.getSender()[1]);
			
			JLabel m2 = new JLabel("Message2");
			m2.setBounds(300, 186, 200, 16);
			add(m2);
			m2.setText(this.getContent()[1]);
		}
		
		
		if(status.length > 2) {
		
			s3.setBounds(17, 231, 61, 16);
			add(s3);
			s3.setText(this.getStatus()[2]);
	
			JLabel d3 = new JLabel("Date3");
			d3.setBounds(88, 231, 113, 16);
			add(d3);
			d3.setText(this.getTime()[2]);
			
	
			JLabel f3 = new JLabel("Sender3");
			f3.setBounds(221, 231, 61, 16);
			add(f3);
			f3.setText(this.getSender()[2]);
			
			JLabel m3 = new JLabel("Message3");
			m3.setBounds(300, 231, 200, 16);
			add(m3);
			m3.setText(this.getContent()[2]);
		}
		JButton btnMarkAsRead = new JButton("Mark As Read");
		btnMarkAsRead.setBounds(333, 64, 133, 31);
		add(btnMarkAsRead);
		btnMarkAsRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					markAsRead();
					display();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		repaint();
	}
	
	public String[] getStatus(){
		String SQL = "SELECT Status FROM SendsMessageToPatient WHERE PatUsername = ?";
		
		List<String> statusList = new ArrayList<String>();
		try(PreparedStatement stmt = conn.prepareStatement(SQL);){
			stmt.setString(1, currentPatient.cp.getPatientUsername());
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String s = rs.getString("Status");
				if(s.equals("0")){
					statusList.add("Unread");
				}else if (s.equals("1")){
					statusList.add("Read");
				}
				;
			}
		} catch (SQLException e){
			System.out.println("Error");
		}
		
		String[] status = (String[]) statusList.toArray(new String[statusList.size()]);

		return status;
	}

	public String[] getContent() {
		String SQL = "SELECT Content FROM SendsMessageToPatient WHERE PatUsername = ?";

		List<String> contentList = new ArrayList<String>();
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				contentList.add(rs.getString("Content"));
			}
		} catch (SQLException e) {
			System.out.println("Error");
		}

		String[] content = (String[]) contentList
				.toArray(new String[contentList.size()]);
		for (int i = 0; i < content.length; i++) {
			System.out.println(content[i]);
		}
		return content;
	}

	public String[] getTime() {
		String SQL = "SELECT DateTime FROM SendsMessageToPatient WHERE PatUsername = ?";

		List<String> timeList = new ArrayList<String>();
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				timeList.add(rs.getString("DateTime").substring(0, 10));
			}
		} catch (SQLException e) {
			System.out.println("Error");
		}

		String[] time = (String[]) timeList
				.toArray(new String[timeList.size()]);
		for (int i = 0; i < time.length; i++) {
			System.out.println(time[i]);
		}
		return time;
	}

	public String[] getSender() {
		String SQL = "SELECT Lname FROM Doctor AS D, SendsMessageToPatient AS S WHERE S.DocUsername = D.DocUsername AND PatUsername = ?";

		List<String> senderList = new ArrayList<String>();
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {
			stmt.setString(1, currentPatient.cp.getPatientUsername());

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				senderList.add("Dr. " + rs.getString("Lname"));
			}
		} catch (SQLException e) {
			System.out.println("Error");
		}

		String[] sender = (String[]) senderList.toArray(new String[senderList
				.size()]);
		for (int i = 0; i < sender.length; i++) {
			System.out.println(sender[i]);
		}
		return sender;
	}
		public boolean markAsRead() throws SQLException{
			String SQL = "UPDATE SendsMessageToPatient SET	Status = ? WHERE	PatUsername =?";
		try (PreparedStatement stmt = conn.prepareStatement(SQL);) {	
			stmt.setString(1, "1");
			stmt.setString(2, currentPatient.cp.getPatientUsername());
			
			
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				System.out.println("marked");

				return true;
			} else {
			
				return false;
			}
		}
	}
		public void display(){
			String[] status = getStatus();
			if(status.length > 0){
				s1.setText((this.getStatus()[0]));
			}
			
			if(status.length > 1) {
				s2.setText((this.getStatus()[1]));
			}
			
			
			if(status.length > 2) {
				s3.setText((this.getStatus()[2]));
			}
		}
}

