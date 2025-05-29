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
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Propuesta;

public class PropuestaDAOJDBC implements PropuestaDao {

    @Override
    public void create(Propuesta propuesta) throws ConexionFallidaException {
        try (Connection conn = ConnectionManager.getConnection()) {

            // Validar que al menos uno de los dos IDs esté cargado
            if (propuesta.getIdAlumno() == 0 && propuesta.getIdEntidad() == 0) {
                throw new SQLException("Debe especificarse un alumno o una entidad para la propuesta.");
            }

            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO propuesta (titulo, area_interes, descripcion, objetivo, aceptada, comentarios, id_alumno, id_entidad) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getAreaInteres());
            statement.setString(3, propuesta.getDescripcion());
            statement.setString(4, propuesta.getObjetivo());
            statement.setBoolean(5, false);
            statement.setString(6, propuesta.getComentarios());

            if (propuesta.getIdAlumno() != 0) {
                statement.setInt(7, propuesta.getIdAlumno());
                statement.setNull(8, java.sql.Types.INTEGER);
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
                statement.setInt(8, propuesta.getIdEntidad());
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la propuesta falló, ninguna fila afectada.");
            }

            // Obtener el ID generado para la propuesta
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int propuestaId = generatedKeys.getInt(1);

                // Insertar las actividades usando el método separado
                insertarActividades(propuestaId, propuesta.getActividades());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la propuesta: " + e.getMessage());
        }
    }

    @Override
    public void update(Propuesta propuesta) throws ConexionFallidaException {
        try (Connection conn = ConnectionManager.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE propuesta SET titulo = ?, area_interes = ?, descripcion = ?, objetivo = ?, aceptada = ?, comentarios = ?, id_alumno = ?, id_entidad = ? WHERE titulo = ?");

            statement.setString(1, propuesta.getTitulo());
            statement.setString(2, propuesta.getAreaInteres());
            statement.setString(3, propuesta.getDescripcion());
            statement.setString(4, propuesta.getObjetivo());
            statement.setInt(5, propuesta.isAceptada());
            statement.setString(6, propuesta.getComentarios());

            // Setear los IDs correctos
            if (propuesta.getIdAlumno() != 0) {
                statement.setInt(7, propuesta.getIdAlumno());
                statement.setNull(8, java.sql.Types.INTEGER);
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
                statement.setInt(8, propuesta.getIdEntidad());
            }

            // En lugar de buscar por id, ahora usamos el título
            statement.setString(9, propuesta.getTitulo());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se encontró ninguna propuesta con el título: " + propuesta.getTitulo());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la propuesta: " + e.getMessage());
        }
    }

    @Override
    public Propuesta find(String titulo) throws ConexionFallidaException {
        Propuesta propuesta = null;

        String sql = "SELECT * FROM propuesta WHERE titulo = ?";

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    int idPropuesta = rs.getInt("id");

                    // Ahora pasar la misma conexión a los métodos secundarios
                    List<Actividad> actividades = cargarActividades(idPropuesta, conn);
                    int idTutorDocente = obtenerIdTutorDocente(idPropuesta, conn);

                    try {
                        propuesta = new Propuesta(
                                rs.getInt("id"),
                                rs.getString("titulo"),
                                rs.getString("area_interes"),
                                rs.getString("objetivo"),
                                rs.getString("descripcion"),
                                rs.getString("comentarios"),
                                rs.getInt("id_alumno"),
                                rs.getInt("aceptada"),
                                rs.getInt("id_entidad"),
                                actividades,
                                idTutorDocente);
                    } catch (InvalidCantHorasExcepcion e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la propuesta: " + e.getMessage(), e);
        }

        return propuesta;
    }

    @Override
    public List<Propuesta> findAll() throws ConexionFallidaException {

        List<Propuesta> propuestas = new ArrayList<>();
        String sql = "SELECT * FROM propuesta";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int idPropuesta = rs.getInt("id");
                List<Actividad> actividades = cargarActividades(idPropuesta, conn);

                int idTutorDocente = obtenerIdTutorDocente(idPropuesta, conn);

                Propuesta propuesta;
                try {
                    propuesta = new Propuesta(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("area_interes"),
                            rs.getString("objetivo"),
                            rs.getString("descripcion"),
                            rs.getString("comentarios"),
                            rs.getInt("id_alumno"),
                            rs.getInt("aceptada"),
                            rs.getInt("id_entidad"),
                            actividades,
                            idTutorDocente // Ahora lo tenés
                    );
                    propuestas.add(propuesta);
                } catch (InvalidCantHorasExcepcion e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las propuestas: " + e.getMessage(), e);
        }
        return propuestas;
    }

    private List<Actividad> cargarActividades(int idPropuesta, Connection conn) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividad WHERE propuesta_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPropuesta);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actividad actividad = new Actividad(
                            rs.getString("nombre_actividad"),
                            rs.getInt("horas"),
                            rs.getString("nombre_actividad"));
                    actividades.add(actividad);
                }
            }
        }
        return actividades;
    }

    public void insertarPropuestaConActividades(PropuestaDTO propuesta, List<ActividadDTO> actividades)
            throws ConexionFallidaException, SQLException {
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

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar propuesta y actividades: " + e.getMessage(), e);
        } finally {
            conn.close();
        }

    }

    private int obtenerIdTutorDocente(int idPropuesta, Connection conn) throws SQLException {
        String sql = "SELECT id_tutor_docente FROM tutor_propuesta WHERE id_propuesta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPropuesta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_tutor_docente");
                }
            }
        }
        return 0; // o un valor que tenga sentido si no hay tutor
    }

    private void insertarActividades(int propuestaId, List<Actividad> actividades)
            throws ConexionFallidaException, SQLException {
        try (Connection conn = ConnectionManager.getConnection()) {
            for (Actividad actividad : actividades) {
                PreparedStatement actividadStmt = conn.prepareStatement(
                        "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)");
                actividadStmt.setString(1, actividad.getNombre());
                actividadStmt.setInt(2, actividad.getHoras());
                actividadStmt.setInt(3, propuestaId);
                actividadStmt.executeUpdate();
            }
        }
    }

    public void actualizarEstadoPropuesta(String id, int estado) throws ConexionFallidaException {
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE propuesta SET aceptada = ? WHERE titulo = ?");
            stmt.setInt(1, estado);
            stmt.setString(2, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró ninguna propuesta con el id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado de la propuesta: " + e.getMessage(), e);
        }
    }
}
