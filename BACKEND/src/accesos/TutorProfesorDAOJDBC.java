package accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.TutorProfesor;

public class TutorProfesorDAOJDBC implements TutorProfesorDao {

	@Override
	public TutorProfesor find(int id) throws ConexionFallidaException {
		String sql =  "SELECT nombre, apellido, dni, correo FROM tutor WHERE id_tutor_docente = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setInt(1, id);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                String nombre = rs.getString("nombre");
	                String apellido = rs.getString("apellido");
	                String dni = rs.getString("dni");
	                String correo = rs.getString("correo");

	                
	                return new TutorProfesor(nombre, apellido, dni, correo);
	            } else {
	                throw new ConexionFallidaException("No se encontró ningún profesor con el id: " + id);
	            }
	        }

	    } catch (SQLException e) {
	        throw new ConexionFallidaException("Error al obtener los datos del profesor: " + e.getMessage());
	    }
	}

}
