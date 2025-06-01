package accesos;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Alumno;

public interface AlumnoDao {

	 Alumno find(int id) throws ConexionFallidaException ;
	 
	 Alumno find(String nombre) throws ConexionFallidaException;
}
