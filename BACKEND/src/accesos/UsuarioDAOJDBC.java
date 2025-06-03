package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) throws ConexionFallidaException {
	    String sql = "INSERT INTO usuario(usuario, contrasena, email, activo, rol, persona) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setString(1, usuario.getUsuario());
	        statement.setString(2, usuario.getContrasena());
	        statement.setString(3, usuario.getEmail());
	        statement.setBoolean(4, usuario.isActivo());
	        statement.setInt(5, usuario.getRol().getCodigo());
	        statement.setString(6, usuario.getPersona().getDni()); // usando el DNI de Persona

	        int filas = statement.executeUpdate();
	        if (filas == 0) {
	            throw new ConexionFallidaException("No se pudo crear el usuario: " + usuario.getUsuario());
	        }

	    } catch (SQLException e) {
	        throw new ConexionFallidaException("Error al insertar usuario: " + e.getMessage());
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
        String sql = "DELETE FROM usuario WHERE usuario=?";
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
    public List<Usuario> findAll() throws ConexionFallidaException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.usuario, u.contrasena, u.email, u.activo, " +
                     "r.id as codigo_rol, r.nombre as nombre_rol, " +
                     "p.dni, p.nombre as nombre_persona, p.apellido " +
                     "FROM usuario u " +
                     "JOIN roles r ON u.rol = r.id " +
                     "LEFT JOIN persona p ON u.persona = p.dni";
        try (Connection conn = ConnectionManager.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
                Persona persona = new Persona(rs.getString("dni"), rs.getString("nombre_persona"), rs.getString("apellido"));
                Usuario usuario = new Usuario(
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("email"),
                        rol,
                        persona
                );
                usuario.setActivo(rs.getBoolean("activo"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new ConexionFallidaException("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

	@Override
	public void update(Usuario Usuario) throws ConexionFallidaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario find(String username) throws ConexionFallidaException {
		String sql = "SELECT u.usuario, u.contrasena, u.email, u.activo, " +
                "r.id as codigo_rol, r.nombre as nombre_rol, " +
                "p.dni, p.nombre as nombre_persona, p.apellido " +
                "FROM usuario u " +
                "JOIN roles r ON u.rol = r.id " +
                "LEFT JOIN persona p ON u.persona = p.dni " +
                "WHERE u.usuario = ?";
   try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {

       statement.setString(1, username);
       try (ResultSet rs = statement.executeQuery()) {
           if (rs.next()) {
               Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
               Persona persona = new Persona(rs.getString("dni"), rs.getString("nombre_persona"), rs.getString("apellido"));
               return new Usuario(
                       rs.getString("usuario"),
                       rs.getString("contrasena"),
                       rs.getString("email"),
                       rol,
                       persona
               );
           }
       }

   } catch (SQLException e) {
       throw new ConexionFallidaException("Error al buscar usuario: " + e.getMessage());
   }
   return null;
	}
    }
