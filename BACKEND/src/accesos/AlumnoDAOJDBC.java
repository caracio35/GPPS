package accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Alumno;

public class AlumnoDAOJDBC implements AlumnoDao{

	@Override
	public Alumno find(int id) throws ConexionFallidaException {
		String sql = "SELECT nombre, apellido, dni, correo FROM alumno WHERE id_alumno = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setInt(1, id);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                String nombre = rs.getString("nombre");
	                String apellido = rs.getString("apellido");
	                String dni = rs.getString("dni");
	                String correo = rs.getString("correo");

	                
	                return new Alumno(nombre, apellido, dni, correo);
	            } else {
	                throw new ConexionFallidaException("No se encontró ningún alumno con el id: " + id);
	            }
	        }

	    } catch (SQLException e) {
	        throw new ConexionFallidaException("Error al obtener los datos del alumno: " + e.getMessage());
	    }
	}

}
