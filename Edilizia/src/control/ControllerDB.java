package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControllerDB {

	private static ControllerDB instance = null;
	private static String USER = "postgres";
	private static String PASS = "postgres";
	private static String DB_URL = "jdbc:postgresql://localhost/edilizia";
	private static String DRIVER_CLASS_NAME = "org.postgresql.Driver";

	private static Connection conn = null;// e se non la inizializzo come null??

	private ControllerDB() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER_CLASS_NAME);
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	// SINGLETON
	public static ControllerDB getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new ControllerDB();
		}
		return instance;
	}

	// METODO PER CONNETTERE
	public Connection connect() {
		return conn;
	}

	// METODO PER DISCONNETTERE
	public void disconnect() throws SQLException {
		conn.close();
	}

}
