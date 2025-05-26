package accesos;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Convenio;

public interface ConvenioDao {
	void create(Convenio convenio)throws ConexionFallidaException;
}
