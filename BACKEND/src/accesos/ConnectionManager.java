package accesos;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;

public class ConnectionManager {
	
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL_DB = "jdbc:mysql://localhost:3306/";
	   
	private static final String USER = "root";
	    
	private static final String PASS = "";

	protected static final String DB_GPPS = "gpps";

	protected static final String DB_SIU = "simulacion_siu";

	    
	    public static Connection getConnection() throws ConexionFallidaException {
	        try {
	            return (Connection) DriverManager.getConnection(URL_DB + DB_GPPS, USER, PASS);
	        } catch (SQLException sqlEx) {
	            throw new ConexionFallidaException("No se pudo conectar a la base '" + DB_GPPS + "'");
	        }
	    }

	   
	    public static Connection getConnectionSiuGuarani() throws ConexionFallidaException {
	        try {
	            return (Connection) DriverManager.getConnection(URL_DB + DB_SIU, USER, PASS);
	        } catch (SQLException sqlEx) {
	            throw new ConexionFallidaException("No se pudo conectar a la base '" + DB_SIU + "'");
	        }
	    }
	}
