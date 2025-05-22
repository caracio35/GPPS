package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaExeption;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Propuesta;

public class PropuestaDAOJDBC implements PropuestaDao {

    @Override
    public void create(Propuesta propuesta) throws ConexionFallidaExeption {
        try {
            Connection conn = ConnectionManager.getConnection();

            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO propuesta (titulo, area_interes, descripcion, objetivo, tutor, aceptada) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getAreaInteres());
            statement.setString(3, propuesta.getDescripcion());
            statement.setString(4, propuesta.getObjetivo());
            statement.setString(5, propuesta.getTutor());
            statement.setBoolean(6, false); // Nueva
                                            // propuesta,
                                            // por
                                            // defecto
                                            // no
                                            // aceptada

            // Cambiar executeQuery() por executeUpdate()
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la propuesta falló, ninguna fila afectada.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int propuestaId = generatedKeys.getInt(1);

                // Guardar las actividades asociadas
                for (Actividad actividad : propuesta.getActividades()) {
                    PreparedStatement actividadStmt = conn.prepareStatement(
                            "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)");
                    actividadStmt.setString(1, actividad.getNombre());
                    actividadStmt.setInt(2, actividad.getHoras());
                    actividadStmt.setInt(3, propuestaId);
                    actividadStmt.executeUpdate(); // También usar executeUpdate() aquí
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
                    "UPDATE propuesta SET titulo = ?, area_interes = ?, descripcion = ?, objetivo = ?, tutor = ?, aceptada = ? WHERE id = ?");

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
                    "SELECT * FROM propuesta WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                propuesta = new Propuesta(
                        rs.getString("titulo"),
                        rs.getString("area_interes"),
                        rs.getString("descripcion"),
                        rs.getString("objetivo"),
                        rs.getString("tutor"));
                propuesta.setId(rs.getInt("id"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));

                // Cargar las actividades asociadas
                PreparedStatement actividadStmt = conn.prepareStatement(
                        "SELECT * FROM actividad WHERE propuesta_id = ?");
                actividadStmt.setInt(1, id);
                ResultSet actividadRs = actividadStmt.executeQuery();

                while (actividadRs.next()) {
                    Actividad actividad = new Actividad(
                            actividadRs.getString("nombre_actividad"),
                            actividadRs.getInt("horas"));
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
                        rs.getString("tutor"));
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

    public List<Propuesta> buscarPropuestas() {
        List<Propuesta> propuestas = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();

            // Consulta para obtener todas las propuestas
            String sqlPropuesta = "SELECT * FROM propuesta";
            PreparedStatement pstmt = conn.prepareStatement(sqlPropuesta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Propuesta propuesta = new Propuesta(
                        rs.getString("titulo"),
                        rs.getString("area_interes"),
                        rs.getString("descripcion"),
                        rs.getString("objetivo"),
                        rs.getString("tutor"));
                propuesta.setId(rs.getInt("id"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));

                // Cargar actividades para cada propuesta
                String sqlActividades = "SELECT * FROM actividad WHERE propuesta_id = ?";
                PreparedStatement pstmtAct = conn.prepareStatement(sqlActividades);
                pstmtAct.setInt(1, propuesta.getId());
                ResultSet rsAct = pstmtAct.executeQuery();

                while (rsAct.next()) {
                    Actividad actividad = new Actividad(
                            rsAct.getString("nombre_actividad"),
                            rsAct.getInt("horas"));
                    propuesta.agregarActividad(actividad);
                }

                propuestas.add(propuesta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar las propuestas: " + e.getMessage(), e);
        } finally {
            ConnectionManager.disconnect();
        }
        return propuestas;
    }

    // ... existing code ...

    public void insertarPropuestaConActividades(PropuestaDTO propuesta, List<ActividadDTO> actividades)
            throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        // Sin total_horas, y con nombre correcto de tabla 'actividad'
        String sqlPropuesta = "INSERT INTO propuesta (titulo, area_interes, objetivo, descripcion) VALUES (?, ?, ? , ?)";
        String sqlActividad = "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)";

        try (
                PreparedStatement stmtPropuesta = conn.prepareStatement(sqlPropuesta, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtActividad = conn.prepareStatement(sqlActividad)) {
            // Insertar propuesta
            stmtPropuesta.setString(1, propuesta.getTitulo());
            stmtPropuesta.setString(2, propuesta.getAreaInteres());
            stmtPropuesta.setString(3, propuesta.getObjetivo());
            stmtPropuesta.setString(4, propuesta.getDescripcion());
            stmtPropuesta.executeUpdate();

            // Obtener ID generado
            ResultSet rs = stmtPropuesta.getGeneratedKeys();
            int idPropuesta = -1;
            if (rs.next()) {
                idPropuesta = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID de la propuesta");// exepcion a cambiar por rulo
            }

            // Insertar actividades
            for (ActividadDTO act : actividades) {
                stmtActividad.setString(1, act.getnombre());
                stmtActividad.setInt(2, act.getHoras());
                stmtActividad.setInt(3, idPropuesta);
                stmtActividad.executeUpdate();
            }

        } catch (ConexionFallidaExeption e) {
            throw new RuntimeException("Error al insertar propuesta y actividades: " + e.getMessage(), e);
        } finally {
            conn.close();
        }
    }

}