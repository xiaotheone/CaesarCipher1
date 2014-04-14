import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * @author hailin
 *
 */
public class DoctorCommunicationPanel extends JPanel{
	private JTextField textField;
	private JTextField textField_1;
	public DoctorCommunicationPanel() {
		setLayout(null);
		
		JLabel lblSelectDoctor = new JLabel("Select Doctor");
		lblSelectDoctor.setBounds(6, 35, 94, 16);
		add(lblSelectDoctor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(89, 31, 100, 27);
		add(comboBox);
		
		JLabel lblSelectPatient = new JLabel("Select Patient");
		lblSelectPatient.setBounds(237, 35, 91, 16);
		add(lblSelectPatient);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(320, 31, 111, 27);
		add(comboBox_1);
		
		textField = new JTextField();
		textField.setBounds(16, 103, 181, 123);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(6, 75, 61, 16);
		add(lblMessage);
		
		JLabel lblMessage_1 = new JLabel("Message:");
		lblMessage_1.setBounds(237, 75, 61, 16);
		add(lblMessage_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(247, 103, 189, 123);
		add(textField_1);
		
		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(154, 249, 117, 29);
		add(btnSendMessage);
	}
}
