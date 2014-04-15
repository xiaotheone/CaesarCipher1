import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * 
 */

/**
 * @author hailin
 *
 */
public class ConnectionManager {
	private static ConnectionManager instance = null;

	private final String USERNAME = "cs4400_Group_7";
	private final String PASSWORD = "AtJE4sFr";
	private final String CONN_STRING =
			"jdbc:mysql://academic-php.cc.gatech.edu/cs4400_Group_7";
	private Connection conn = null;
	
	private ConnectionManager() {
	}

	public static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	private boolean openConnection()
	{
		try {
			conn = (Connection) DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			return true;
		}
		catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}
	
	public Connection getConnection()
	{
		if (conn == null) {
			if (openConnection()) {
				System.out.println("Connection opened");
				return conn;
			} else {
				return null;
			}
		}
		return conn;
	}
	
	public void close() {
		System.out.println("Closing connection");
		try {
			conn.close();
			conn = null;
		} catch (Exception e) {
		}
	}


}
