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
                    "INSERT INTO propuesta (titulo, area_interes, descripcion, objetivo, aceptada, comentarios, id_alumno, id_entidad) " +
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
            statement.setBoolean(5, propuesta.isAceptada());
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

        try (Connection conn = ConnectionManager.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM propuesta WHERE titulo = ?");
            statement.setString(1, titulo);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int idPropuesta = rs.getInt("id"); // Guardamos el id solo en el DAO

                propuesta = new Propuesta();
                propuesta.setTitulo(rs.getString("titulo"));
                propuesta.setAreaInteres(rs.getString("area_interes"));
                propuesta.setDescripcion(rs.getString("descripcion"));
                propuesta.setObjetivo(rs.getString("objetivo"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));
                propuesta.setComentarios(rs.getString("comentarios"));
                propuesta.setIdAlumno(rs.getInt("id_alumno"));
                propuesta.setIdEntidad(rs.getInt("id_entidad"));

                // Cargar actividades usando el método modularizado
                cargarActividades(idPropuesta, propuesta);
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
                int idPropuesta = rs.getInt("id"); // Obtenemos el id de la propuesta

                Propuesta propuesta = new Propuesta();
                propuesta.setTitulo(rs.getString("titulo"));
                propuesta.setAreaInteres(rs.getString("area_interes"));
                propuesta.setDescripcion(rs.getString("descripcion"));
                propuesta.setObjetivo(rs.getString("objetivo"));
                propuesta.setAceptada(rs.getBoolean("aceptada"));
                propuesta.setComentarios(rs.getString("comentarios"));
                propuesta.setIdAlumno(rs.getInt("id_alumno"));
                propuesta.setIdEntidad(rs.getInt("id_entidad"));

                // Cargar actividades usando el método modificado
                cargarActividades(idPropuesta, propuesta);

                propuestas.add(propuesta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener las propuestas: " + e.getMessage(), e);
        }

        return propuestas;
    }

    

	private void cargarActividades(int idPropuesta, Propuesta propuesta) throws SQLException , ConexionFallidaException {
        String sqlActividades = "SELECT a.nombre_actividad, a.horas, p.titulo AS nombre_propuesta " +
                                 "FROM actividad a " +
                                 "JOIN propuesta p ON a.propuesta_id = p.id " +
                                 "WHERE a.propuesta_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlActividades)) {

            stmt.setInt(1, idPropuesta); // Buscamos por ID de la propuesta
            try (ResultSet rs = stmt.executeQuery()) {
                if (propuesta.getActividades() == null) {
                    propuesta.setActividades(new ArrayList<>());
                }
                while (rs.next()) {
                    Actividad actividad = new Actividad(
                            rs.getString("nombre_actividad"),
                            rs.getInt("horas"),
                            rs.getString("nombre_propuesta")
                    );
                    propuesta.getActividades().add(actividad);
                }
            }
        }
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
    private void insertarActividades(int propuestaId, List<Actividad> actividades) throws  ConexionFallidaException,SQLException {
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
}