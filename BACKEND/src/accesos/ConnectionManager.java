package accesos;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;

public class ConnectionManager {
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL_DB = "jdbc:mysql://localhost:3306/";
    protected static String DB = "gpps";
    protected static String user = "root";
    protected static String pass = "";

    public static Connection getConnection() throws ConexionFallidaException {
        try {
            // ⚠️ Crea una conexión nueva CADA VEZ
            return (Connection) DriverManager.getConnection(URL_DB + DB, user, pass);
        } catch (SQLException sqlEx) {
            throw new ConexionFallidaException(DB);
        }
    }
}
