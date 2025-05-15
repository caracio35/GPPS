package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Actividad;

public class PropuestaDAOJDBC implements PropuestaDao {

    @Override
    public void create(Propuesta propuesta) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO propuesta (titulo, area_interes, descripcion, objetivo, tutor, aceptada) VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getAreaInteres());
            statement.setString(3, propuesta.getDescripcion());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getTutor());
            statement.setBoolean(6, false); // Nueva propuesta, por defecto no aceptada

            statement.executeUpdate();

            // Obtener el ID generado para la propuesta
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int propuestaId = generatedKeys.getInt(1);
                
                // Guardar las actividades asociadas
                for (Actividad actividad : propuesta.getActividades()) {
                    PreparedStatement actividadStmt = conn.prepareStatement(
                        "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)"
                    );
                    actividadStmt.setString(1, actividad.getNombre());
                    actividadStmt.setInt(2, actividad.getHoras());
                    actividadStmt.setInt(3, propuestaId);
                    actividadStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Propuesta propuesta) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE propuesta SET titulo = ?, area_interes = ?, descripcion = ?, objetivo = ?, tutor = ?, aceptada = ? WHERE id = ?"
            );

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getAreaInteres());
            statement.setString(3, propuesta.getDescripcion());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getTutor());
            statement.setBoolean(6, propuesta.isAceptada());
            statement.setInt(7, propuesta.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Propuesta find(Integer id) {
        Propuesta propuesta = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM propuesta WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("area_interes"),
                    rs.getString("descripcion"),
                    rs.getString("objetivo"),
                    rs.getString("tutor")
                );
                propuesta.setId(rs.getInt("id"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));

                // Cargar las actividades asociadas
                PreparedStatement actividadStmt = conn.prepareStatement(
                    "SELECT * FROM actividad WHERE propuesta_id = ?"
                );
                actividadStmt.setInt(1, id);
                ResultSet actividadRs = actividadStmt.executeQuery();

                while (actividadRs.next()) {
                    Actividad actividad = new Actividad(
                        actividadRs.getString("nombre_actividad"),
                        actividadRs.getInt("horas")
                    );
                    propuesta.agregarActividad(actividad);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return propuesta;
    }

    @Override
    public List<Propuesta> findAll() {
        List<Propuesta> propuestas = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM propuesta");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Propuesta propuesta = new Propuesta(
                    rs.getString("titulo"),
                    rs.getString("area_interes"),
                    rs.getString("descripcion"),
                    rs.getString("objetivo"),
                    rs.getString("tutor")
                );
                propuesta.setId(rs.getInt("id"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));
                propuestas.add(propuesta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las propuestas: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return propuestas;
    }
}