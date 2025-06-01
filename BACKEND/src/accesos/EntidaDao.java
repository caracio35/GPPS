package accesos;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Entidad;

public interface EntidaDao {

	Entidad find(int id) throws ConexionFallidaException;
	
	Entidad find(String string) throws ConexionFallidaException ; 
}
