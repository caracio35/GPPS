package accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Convenio;

public class ConvenioDAOJDBC implements ConvenioDao{

	public void create(Convenio convenio) throws ConexionFallidaException {
	    try {
	        Connection conn = (Connection) ConnectionManager.getConnection();

	        int idPropuesta = obtenerIdPropuestaPorTitulo(convenio.getPropuesta().getTitulo());
	        
	        PreparedStatement statement = conn.prepareStatement(
	        	    "INSERT INTO convenio (fecha_generacion, estado, archivo, propuesta_id, alumno_usuario, tutor_academico_usuario) " +
	        	    "VALUES (?, ?, ?, ?, ?, ?)");

	        // Setear los valores de cada columna
	        statement.setDate(1, new java.sql.Date(convenio.getFechaGeneracion().getTime()));
	        statement.setString(2, convenio.getEstado());
	        statement.setString(3, convenio.getArchivo());
	        statement.setInt(4, idPropuesta);
	        statement.setInt(5, convenio.getPropuesta().getIdAlumno());
	        statement.setInt(6, convenio.getPropuesta().getIdPorfesor());
	   
	        // Ejecutar la inserción
	        int affectedRows = statement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("La creación del convenio falló, ninguna fila afectada.");
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al crear el convenio: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    
	    }
	    }
	
	
	public int obtenerIdPropuestaPorTitulo(String tituloPropuesta) throws ConexionFallidaException {
	    int idPropuesta = -1;

	    try {
	        Connection conn = (Connection) ConnectionManager.getConnection();

	        PreparedStatement buscarPropuesta = conn.prepareStatement(
	            "SELECT id FROM propuesta WHERE titulo = ?"
	        );
	        buscarPropuesta.setString(1, tituloPropuesta);
	        ResultSet rs = buscarPropuesta.executeQuery();

	        if (rs.next()) {
	            idPropuesta = rs.getInt("id");
	        } else {
	            throw new SQLException("No se encontró la propuesta con el título: " + tituloPropuesta);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al obtener el id de la propuesta: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return idPropuesta;
	}
	}
