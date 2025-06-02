package accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	void create(Rol rol);

	void update(Rol rol);

	void remove(Long id);

	void remove(Rol rol);

	Rol find(Integer codigo) throws ConexionFallidaException;

	List<Rol> findAll()throws ConexionFallidaException;

}
