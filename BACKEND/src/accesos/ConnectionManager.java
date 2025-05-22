package accesos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.edu.unrn.seminario.exception.ConexionFallidaExeption;

public class ConnectionManager {
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String URL_DB = "jdbc:mysql://localhost:3306/";
	protected static String DB = "gpps";
	protected static String user = "root";
	protected static String pass = "";
	protected static Connection conn = null;

	public static void connect() throws ConexionFallidaExeption{
		try {
			conn = DriverManager.getConnection(URL_DB + DB, user, pass);
		} catch (SQLException sqlEx) {
			throw new ConexionFallidaExeption();
		}
	}

	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void reconnect() throws ConexionFallidaExeption {
		disconnect();
		connect();
	}

	public static Connection getConnection() throws ConexionFallidaExeption {
		if (conn == null) {
			connect();
		}
		return conn;
	}

}
