import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class MessagePanel extends JPanel{
	private JTable table;
	public MessagePanel() {
		setLayout(null);
		
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBorder(new LineBorder(Color.BLACK, 3, true));
		table.setBounds(167, 197, 110, -69);
		add(table);
	}
}
