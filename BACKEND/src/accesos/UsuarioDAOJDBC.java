package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO usuarios(usuario, contrasena, email, nombre, activo,rol) "
							+ "VALUES (?, ?, ?, ?, ?, ?)");

			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getNombre());
			statement.setString(4, usuario.getEmail());
			statement.setBoolean(5, usuario.isActivo());
			statement.setInt(6, usuario.getRol().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Usuario usuario) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"UPDATE usuarios SET contrasena=?, nombre=?, email=?, activo=?, rol=? WHERE usuario=?");
			statement.setString(1, usuario.getContrasena());
			statement.setString(2, usuario.getNombre());
			statement.setString(3, usuario.getEmail());
			statement.setBoolean(4, usuario.isActivo());
			statement.setInt(5, usuario.getRol().getCodigo());
			statement.setString(6, usuario.getUsuario());
			int cantidad = statement.executeUpdate();
			if (cantidad == 0) {
				System.out.println("No se encontró el usuario para actualizar.");
			}
		} catch (SQLException e) {
			System.out.println("Error al actualizar usuario: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(Long id) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"DELETE FROM usuarios WHERE usuario=?");
			statement.setString(1, String.valueOf(id));
			int cantidad = statement.executeUpdate();
			if (cantidad == 0) {
				System.out.println("No se encontró el usuario para eliminar.");
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar usuario: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(Usuario usuario) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"DELETE FROM usuarios WHERE usuario=?");
			statement.setString(1, usuario.getUsuario());
			int cantidad = statement.executeUpdate();
			if (cantidad == 0) {
				System.out.println("No se encontró el usuario para eliminar.");
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar usuario: " + e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public Usuario find(String username) {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+
							"FROM usuarios u JOIN roles r ON (u.rol = r.codigo) WHERE u.usuario = ?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				usuario = new Usuario(
						rs.getString("usuario"),
						rs.getString("contrasena"),
						rs.getString("nombre"),
						rs.getString("email"),
						rol);
			}
		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
		} finally {
			ConnectionManager.disconnect();
		}
		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+
							"FROM usuarios u JOIN roles r ON (u.rol = r.codigo)");
			while (rs.next()) {
				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				Usuario usuario = new Usuario(
						rs.getString("usuario"),
						rs.getString("contrasena"),
						rs.getString("nombre"),
						rs.getString("email"),
						rol);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} finally {
			ConnectionManager.disconnect();
		}
		return usuarios;
	}

}
