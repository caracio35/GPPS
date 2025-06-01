package accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Entidad;

public class EntidadDAOJDBC implements EntidaDao {

	@Override
	public Entidad find(int id) throws ConexionFallidaException {
		 String sql = "SELECT nombre, telefono, correo, cuit FROM entidad WHERE id_entidad = ?";

		    try (Connection conn = ConnectionManager.getConnection();
		         PreparedStatement statement = conn.prepareStatement(sql)) {

		        statement.setInt(1, id);
		        try (ResultSet rs = statement.executeQuery()) {
		            if (rs.next()) {
		                String nombre = rs.getString("nombre");
		                String telefono = rs.getString("telefono");
		                String correo = rs.getString("correo");
		                String cuit = rs.getString("cuit");

		                return new Entidad(nombre, telefono, correo, cuit);
		            } else {
		                throw new ConexionFallidaException("No se encontró ninguna entidad con el id: " + id);
		            }
		        }

		    } catch (SQLException e) {
		        throw new ConexionFallidaException("Error al obtener los datos de la entidad: " + e.getMessage());
		    }
		}

	public Entidad find(String nombre) throws ConexionFallidaException {
	    String sql = "SELECT id_entidad, nombre, telefono, correo, cuit FROM entidad WHERE nombre = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setString(1, nombre);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                int id = rs.getInt("id_entidad");
	                String telefono = rs.getString("telefono");
	                String correo = rs.getString("correo");
	                String cuit = rs.getString("cuit");

	                Entidad entidad = new Entidad(nombre, telefono, correo, cuit);
	                entidad.setId(id);
	                return entidad ;
	            } else {
	                return null; // No encontrada
	            }
	        }

	    } catch (SQLException e) {
	        throw new ConexionFallidaException("Error al buscar entidad por nombre: " + e.getMessage());
	    }
	}
	

}
