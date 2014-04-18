import javax.swing.JPanel;
import javax.swing.JLabel;
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
public class PatientHomepagePanel extends JPanel{
	public PatientHomepagePanel() {
		setLayout(null);
		
		JLabel lblPatientHomepage = new JLabel("Patient Homepage");
		lblPatientHomepage.setBounds(137, 27, 136, 16);
		add(lblPatientHomepage);
		
		JButton btnNewButton = new JButton("Make Appointment");
		btnNewButton.setBounds(26, 98, 171, 29);
		add(btnNewButton);
		
		JButton btnViewVisitHistory = new JButton("View Visit History");
		btnViewVisitHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnViewVisitHistory.setBounds(26, 143, 171, 29);
		add(btnViewVisitHistory);
		
		JButton btnOrderMedication = new JButton("Order Medication");
		btnOrderMedication.setBounds(26, 195, 171, 29);
		add(btnOrderMedication);
		
		JButton btnCommunicate = new JButton("Communicate");
		btnCommunicate.setBounds(26, 236, 171, 29);
		add(btnCommunicate);
		
		JButton btnRateADcotor = new JButton("Rate A Doctor");
		btnRateADcotor.setBounds(247, 98, 160, 29);
		add(btnRateADcotor);
		
		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.setBounds(247, 143, 160, 29);
		add(btnEditProfile);
	}
}
