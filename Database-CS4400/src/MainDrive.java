import java.sql.SQLException;


/**
 * The main class of whole application
 * This is GT Medical Records System application base by 
 * java, mysql, JDBC
 * @author bochen
 *
 */
public class MainDrive {

//
//	private static final String USERNAME = "cs4400_Group_7";
//	private static final String PASSWORD = "AtJE4sFr";
//	private static final String CONN_STRING = "jdbc:mysql://academic-php.cc.gatech.edu/cs4400_Group_7";

	/**
	 * creating the main frame of the app
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {

		
		//ConnectionManager.getInstance();
		
		mainFrame app = new mainFrame();
		
		//ConnectionManager.getInstance().close();
	}
}
