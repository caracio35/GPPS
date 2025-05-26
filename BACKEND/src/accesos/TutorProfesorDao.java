package accesos;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.TutorProfesor;

public interface TutorProfesorDao {

	TutorProfesor find(int id) throws ConexionFallidaException;
}
