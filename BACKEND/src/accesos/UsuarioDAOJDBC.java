package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

    @Override
    public void create(Usuario usuario) throws ConexionFallidaException {
        String sql = "INSERT INTO usuarios(usuario, contrasena, email, nombre, activo, rol) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getUsuario());
            statement.setString(2, usuario.getContrasena());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getNombre());
            statement.setBoolean(5, usuario.isActivo());
            statement.setInt(6, usuario.getRol().getCodigo());

            int filas = statement.executeUpdate();
            if (filas == 0) {
                throw new ConexionFallidaException("No se pudo crear el usuario: " + usuario.getUsuario());
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public void update(Usuario usuario) throws ConexionFallidaException {
        String sql = "UPDATE usuarios SET contrasena=?, nombre=?, email=?, activo=?, rol=? WHERE usuario=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getContrasena());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getEmail());
            statement.setBoolean(4, usuario.isActivo());
            statement.setInt(5, usuario.getRol().getCodigo());
            statement.setString(6, usuario.getUsuario());

            int filas = statement.executeUpdate();
            if (filas == 0) {
                throw new ConexionFallidaException("No se encontró el usuario para actualizar: " + usuario.getUsuario());
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void remove(Long id) throws ConexionFallidaException {
        removeByUsername(String.valueOf(id));
    }

    @Override
    public void remove(Usuario usuario) throws ConexionFallidaException {
        removeByUsername(usuario.getUsuario());
    }

    private void removeByUsername(String username) throws ConexionFallidaException {
        String sql = "DELETE FROM usuarios WHERE usuario=?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            int filas = statement.executeUpdate();
            if (filas == 0) {
                throw new ConexionFallidaException("No se encontró el usuario para eliminar: " + username);
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario find(String username) throws ConexionFallidaException {
        String sql = "SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
                   + "FROM usuarios u JOIN roles r ON (u.rol = r.codigo) WHERE u.usuario = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
                    return new Usuario(
                            rs.getString("usuario"),
                            rs.getString("contrasena"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rol);
                }
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() throws ConexionFallidaException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.usuario, u.contrasena, u.nombre, u.email, u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
                + "FROM usuario u JOIN roles r ON (u.rol = r.codigo)";
        try (Connection conn = ConnectionManager.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

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
            throw new ConexionFallidaException("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }
}
