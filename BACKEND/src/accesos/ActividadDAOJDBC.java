package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Actividad;

public class ActividadDAOJDBC implements ActividadDao {

    @Override
    public void create(Actividad actividad) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)"
            );

            statement.setString(1, actividad.getNombre());
            statement.setInt(2, actividad.getHoras());
            statement.setInt(3, actividad.getPropuestaId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la actividad: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Actividad actividad) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE actividad SET nombre_actividad = ?, horas = ? WHERE id = ?"
            );

            statement.setString(1, actividad.getNombre());
            statement.setInt(2, actividad.getHoras());
            statement.setInt(3, actividad.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la actividad: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Actividad find(Integer id) {
        Actividad actividad = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM actividad WHERE id = ?"
            );
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                actividad = new Actividad(
                    rs.getString("nombre_actividad"),
                    rs.getInt("horas")
                );
                actividad.setId(rs.getInt("id"));
                actividad.setPropuestaId(rs.getInt("propuesta_id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la actividad: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return actividad;
    }

    @Override
    public List<Actividad> findAll() {
        List<Actividad> actividades = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM actividad");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getString("nombre_actividad"),
                    rs.getInt("horas")
                );
                actividad.setId(rs.getInt("id"));
                actividad.setPropuestaId(rs.getInt("propuesta_id"));
                actividades.add(actividad);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las actividades: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return actividades;
    }

    public List<Actividad> findByPropuesta(Integer propuestaId) {
        List<Actividad> actividades = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM actividad WHERE propuesta_id = ?"
            );
            statement.setInt(1, propuestaId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getString("nombre_actividad"),
                    rs.getInt("horas")
                );
                actividad.setId(rs.getInt("id"));
                actividad.setPropuestaId(rs.getInt("propuesta_id"));
                actividades.add(actividad);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las actividades de la propuesta: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return actividades;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }
}