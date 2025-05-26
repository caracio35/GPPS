package accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario)throws ConexionFallidaException;

	void update(Usuario Usuario)throws ConexionFallidaException;

	void remove(Long id)throws ConexionFallidaException;

	void remove(Usuario Usuario)throws ConexionFallidaException;

	Usuario find(String username)throws ConexionFallidaException;

	List<Usuario> findAll()throws ConexionFallidaException;

}
