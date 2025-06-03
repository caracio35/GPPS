package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PropuestaDAOJDBC implements PropuestaDao {

	@Override
	public void create(Propuesta propuesta) throws ConexionFallidaException {
		String sql = "INSERT INTO propuesta (titulo, descripcion, area_interes, objetivo, comentarios, aceptada, creador, alumno, tutor, profesor) "
				+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, propuesta.getTitulo());
			stmt.setString(2, propuesta.getDescripcion());
			stmt.setString(3, propuesta.getAreaInteres());
			stmt.setString(4, propuesta.getObjetivo());
			stmt.setString(5, propuesta.getComentarios());
			stmt.setBoolean(6, false); // siempre se crea como no aceptada

			// Insertar los usuarios vinculados
			stmt.setString(7, propuesta.getCreador() != null ? propuesta.getCreador().getUsuario() : null);
			stmt.setString(8, propuesta.getAlumno() != null ? propuesta.getAlumno().getUsuario() : null);
			stmt.setString(9, propuesta.getTutor() != null ? propuesta.getTutor().getUsuario() : null);
			stmt.setString(10, propuesta.getProfesor() != null ? propuesta.getProfesor().getUsuario() : null);

			int filas = stmt.executeUpdate();
			if (filas == 0)
				throw new ConexionFallidaException("No se pudo insertar la propuesta.");

			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					int propuestaId = rs.getInt(1);
					insertarActividades(propuestaId, propuesta.getActividades(), conn);
				}
			}

		} catch (SQLException e) {
			throw new ConexionFallidaException("Error al crear propuesta: " + e.getMessage());
		}

	}

	@Override
	public void update(Propuesta propuesta) throws ConexionFallidaException {
		String sql = "UPDATE propuesta SET descripcion = ?, area_interes = ?, objetivo = ?, comentarios = ?, aceptada = ?, "
				+
				"creador = ?, alumno = ?, tutor = ?, profesor = ? WHERE titulo = ?";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, propuesta.getDescripcion());
			stmt.setString(2, propuesta.getAreaInteres());
			stmt.setString(3, propuesta.getObjetivo());
			stmt.setString(4, propuesta.getComentarios());
			stmt.setInt(5, propuesta.isAceptada());

			stmt.setString(6, propuesta.getCreador() != null ? propuesta.getCreador().getUsuario() : null);
			stmt.setString(7, propuesta.getAlumno() != null ? propuesta.getAlumno().getUsuario() : null);
			stmt.setString(8, propuesta.getTutor() != null ? propuesta.getTutor().getUsuario() : null);
			stmt.setString(9, propuesta.getProfesor() != null ? propuesta.getProfesor().getUsuario() : null);

			stmt.setString(10, propuesta.getTitulo());

			int filas = stmt.executeUpdate();
			if (filas == 0)
				throw new ConexionFallidaException(
						"No se encontró la propuesta a actualizar: " + propuesta.getTitulo());

		} catch (SQLException e) {
			throw new ConexionFallidaException("Error al actualizar propuesta: " + e.getMessage());
		}
	}

	@Override
	public Propuesta find(String titulo) throws ConexionFallidaException {
		return null;

	}

	@Override
	public List<Propuesta> findAll() throws ConexionFallidaException {
		List<Propuesta> propuestas = new ArrayList<>();
		String sql = "SELECT * FROM propuesta";

		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String titulo = rs.getString("titulo");
				String descripcion = rs.getString("descripcion");
				String areaInteres = rs.getString("area_interes");
				String objetivo = rs.getString("objetivo");
				String comentarios = rs.getString("comentarios");
				int aceptada = rs.getObject("aceptada") != null ? rs.getInt("aceptada") : null;

				String creadorUsername = rs.getString("creador");
				String alumnoUsername = rs.getString("alumno");
				String tutorUsername = rs.getString("tutor");
				String profesorUsername = rs.getString("profesor");

				// Cargar usuarios relacionados
				Usuario creador = buscarUsuarioPorUsername(creadorUsername, conn);
				Usuario alumno = buscarUsuarioPorUsername(alumnoUsername, conn);
				Usuario tutor = buscarUsuarioPorUsername(tutorUsername, conn);
				Usuario profesor = buscarUsuarioPorUsername(profesorUsername, conn);

				// Cargar actividades relacionadas
				List<Actividad> actividades = cargarActividades(rs.getInt("id"), conn);

				try {
					Propuesta propuesta = new Propuesta(
							titulo,
							descripcion,
							areaInteres,
							objetivo,
							comentarios,
							aceptada,
							creador,
							alumno,
							tutor,
							profesor,
							actividades);
					propuesta.setActividades(actividades);
					propuestas.add(propuesta);

				} catch (InvalidCantHorasExcepcion e) {
					System.err.println("Propuesta inválida (" + titulo + "): " + e.getMessage());
				}
			}

		} catch (SQLException e) {
			throw new ConexionFallidaException("Error al obtener propuestas: " + e.getMessage());
		}
		return propuestas;
	}

	@Override
	public void registrarAlumnoApropuesta(String nombrePropuesta, int idAlumno) throws ConexionFallidaException {
		// TODO Auto-generated method stub

	}

	private List<Actividad> cargarActividades(int idPropuesta, Connection conn) throws SQLException {
		List<Actividad> actividades = new ArrayList<>();
		String sql = "SELECT * FROM actividad WHERE propuesta_id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idPropuesta);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					actividades.add(new Actividad(
							rs.getString("nombre_actividad"),
							rs.getInt("horas")));
				}
			}
		}
		return actividades;
	}

	private void insertarActividades(int propuestaId, List<Actividad> actividades, Connection conn)
			throws SQLException {
		String sql = "INSERT INTO actividad (nombre_actividad, horas, propuesta_id) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (Actividad act : actividades) {
				stmt.setString(1, act.getNombreActividad());
				stmt.setInt(2, act.getHoras());
				stmt.setInt(3, propuestaId);
				stmt.addBatch();
			}
			stmt.executeBatch();
		}
	}

	private Usuario buscarUsuarioPorUsername(String username, Connection conn) throws SQLException {
		if (username == null)
			return null;

		String sql = "SELECT u.usuario, u.contrasena, u.email, u.activo, u.rol, u.persona, " +
				"r.nombre AS nombre_rol, " +
				"p.dni, p.nombre AS nombre_persona, p.apellido AS apellido_persona " +
				"FROM usuario u " +
				"LEFT JOIN roles r ON u.rol = r.id " +
				"LEFT JOIN persona p ON u.persona = p.dni " +
				"WHERE u.usuario = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// Datos básicos de Usuario
					String contrasena = rs.getString("contrasena");
					String email = rs.getString("email");
					boolean activo = rs.getBoolean("activo");

					// Datos de Rol
					int rolId = rs.getInt("rol");
					String nombreRol = rs.getString("nombre_rol");
					Rol rol = new Rol(rolId, nombreRol);
					rol.setActivo(true); // Suponemos que por defecto el rol está activo

					// Datos de Persona
					String dni = rs.getString("dni");
					String nombrePersona = rs.getString("nombre_persona");
					String apellidoPersona = rs.getString("apellido_persona");
					Persona persona = new Persona(dni, nombrePersona, apellidoPersona);

					// Construir el Usuario
					Usuario usuario = new Usuario(username, contrasena, email, rol, persona);
					usuario.setActivo(activo);

					return usuario;
				}
			}
		}
		return null;
	}

}
