package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Actividad;

public class ActividadDAOJDBC implements ActividadDao {

	@Override
	public void create(Actividad actividad) throws ConexionFallidaException {
	    String sqlBuscarId = "SELECT id FROM propuesta WHERE titulo = ?";
	    String sqlInsert = "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)";

	    try (Connection conn = ConnectionManager.getConnection()) {

	        // 1️⃣ Buscar el id de la propuesta
	        int idPropuesta;
	        try (PreparedStatement buscarStmt = conn.prepareStatement(sqlBuscarId)) {
	            buscarStmt.setString(1, actividad.getNombrePropuesta());
	            try (ResultSet rs = buscarStmt.executeQuery()) {
	                if (rs.next()) {
	                    idPropuesta = rs.getInt("id");
	                } else {
	                    throw new SQLException();
	                }
	            }
	        }

	        // 2️⃣ Insertar la actividad
	        try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
	            insertStmt.setString(1, actividad.getNombre());
	            insertStmt.setInt(2, actividad.getHoras());
	            insertStmt.setInt(3, idPropuesta);

	            insertStmt.executeUpdate();
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al crear la actividad: " + e.getMessage(), e);
	    }
	}

	@Override
	public void update(Actividad actividad) throws ConexionFallidaException {
	    String sql = "UPDATE actividad SET nombre_actividad = ?, horas = ? WHERE id = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setString(1, actividad.getNombre());
	        statement.setInt(2, actividad.getHoras());
	        

	        statement.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al actualizar la actividad: " + e.getMessage(), e);
	    }
	}

	@Override
	public Actividad find(String nombreActividad) throws ConexionFallidaException {
	    Actividad actividad = null;
	    String sql = "SELECT a.nombre_actividad, a.horas, p.titulo AS nombre_propuesta " +
	                  "FROM actividad a " +
	                  "JOIN propuesta p ON a.propuesta_id = p.id " +
	                  "WHERE a.nombre_actividad = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setString(1, nombreActividad);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                actividad = new Actividad(
	                        rs.getString("nombre_actividad"),
	                        rs.getInt("horas"),
	                        rs.getString("nombre_propuesta")
	                );
	               
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al buscar la actividad: " + e.getMessage(), e);
	    }

	    return actividad;
	}

	@Override
	public List<Actividad> findAll() throws ConexionFallidaException{
	    List<Actividad> actividades = new ArrayList<>();
	    String sql = "SELECT a.nombre_actividad, a.horas, p.titulo AS nombre_propuesta " +
	                  "FROM actividad a " +
	                  "JOIN propuesta p ON a.propuesta_id = p.id";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {

	        while (rs.next()) {
	            Actividad actividad = new Actividad(
	                    rs.getString("nombre_actividad"),
	                    rs.getInt("horas"),
	                    rs.getString("nombre_propuesta")
	            );
	            actividades.add(actividad);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error al obtener las actividades: " + e.getMessage(), e);
	    }

	    return actividades;
	}
}