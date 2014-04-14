import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class PatientCommunicationPanel extends JPanel{
	public PatientCommunicationPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select");
		lblNewLabel.setBounds(66, 54, 61, 16);
		add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(160, 50, 126, 27);
		add(comboBox);
		
		JLabel lblMessages = new JLabel("Message");
		lblMessages.setBounds(66, 100, 61, 16);
		add(lblMessages);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(170, 100, 220, 121);
		add(textArea);
		
		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.setBounds(327, 252, 117, 29);
		add(btnNewButton);
	}
}
