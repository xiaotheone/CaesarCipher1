import javax.swing.JFrame;


@SuppressWarnings("serial")
public class mainFrame extends JFrame{
	
	public mainFrame(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 550);
		setTitle("GTMR");
		setResizable(true);
		setVisible(true);

		add(new LoginPanel());
		repaint();
	}
}
