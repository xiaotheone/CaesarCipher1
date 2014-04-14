import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The main class of whole application
 * This is GT Medical Records System application base by 
 * java, mysql, JDBC
 * @author bochen
 *
 */
public class MainDrive {


	private static final String USERNAME = "cs4400_Group_7";
	private static final String PASSWORD = "AtJE4sFr";
	private static final String CONN_STRING = "jdbc:mysql://academic-php.cc.gatech.edu/cs4400_Group_7";

	/**
	 * creating the main frame of the app
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {

		//declare your resources first and initially set them to null
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		mainFrame app = new mainFrame();
		
		//instantiate resources within a try block
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("Connected...");

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT Username FROM User");
			//result.last();
			//int count = 0;
			/*while(result.next()){
				count += 1;
				System.out.println("Username: " + result.getString("Username"));

			}
			System.out.println("Total user number: " + count);*/
			LoginPanel.importResult(result);

		} catch (SQLException e) {
			System.err.println(e);
		} 
		
		//close them in a finally block first, making sure that they're not null
		//so that you don't cause yet another error
		/*finally {
			if (result != null) {
				result.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}*/
	}
}
