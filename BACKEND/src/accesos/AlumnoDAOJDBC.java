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

	    try (Connection conn = ConnectionManager.getConnectionSiuGuarani();
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

	@Override
	public Alumno find(String nombre) throws ConexionFallidaException {
	    String sql = "SELECT id_alumno, nombre, apellido, dni, correo FROM alumno WHERE nombre = ?";

	    try (Connection conn = ConnectionManager.getConnectionSiuGuarani();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setString(1, nombre);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                int id = rs.getInt("id_alumno");
	                String nombreRecuperado = rs.getString("nombre");
	                String apellido = rs.getString("apellido");
	                String dni = rs.getString("dni");
	                String correo = rs.getString("correo");

	               
	                Alumno alumno = new Alumno(nombreRecuperado, apellido, dni, correo);
	                alumno.setId(id); 
	                return alumno;
	            } else {
	                throw new ConexionFallidaException("No se encontró ningún alumno con el nombre: " + nombre);
	            }
	        }

	    } catch (SQLException e) {
	        throw new ConexionFallidaException("Error al obtener los datos del alumno: " + e.getMessage());
	    }
	}
	
}
