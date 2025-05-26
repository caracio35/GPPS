package accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Convenio;

public class ConvenioDAOJDBC implements ConvenioDao {

    @Override
    public void create(Convenio convenio) throws ConexionFallidaException {
        String sql = "INSERT INTO convenio (fecha_generacion, estado, archivo, propuesta_id, alumno_usuario, tutor_academico_usuario) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            int idPropuesta = obtenerIdPropuestaPorTitulo(convenio.getPropuesta().getTitulo());

            statement.setDate(1, new java.sql.Date(convenio.getFechaGeneracion().getTime()));
            statement.setString(2, convenio.getEstado());
            statement.setString(3, convenio.getArchivo());
            statement.setInt(4, idPropuesta);
            statement.setInt(5, convenio.getPropuesta().getIdAlumno());
            statement.setInt(6, convenio.getPropuesta().getIdPorfesor());

            int filas = statement.executeUpdate();
            if (filas == 0) {
                throw new ConexionFallidaException("La creación del convenio falló: no se afectaron filas.");
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al crear el convenio: " + e.getMessage());
        }
    }

    public int obtenerIdPropuestaPorTitulo(String tituloPropuesta) throws ConexionFallidaException {
        String sql = "SELECT id FROM propuesta WHERE titulo = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, tituloPropuesta);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new ConexionFallidaException("No se encontró la propuesta con el título: " + tituloPropuesta);
                }
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al obtener el id de la propuesta: " + e.getMessage());
        }
    }
}
