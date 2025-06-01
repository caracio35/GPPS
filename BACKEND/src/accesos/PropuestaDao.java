package accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Propuesta;

public interface PropuestaDao {
    void create(Propuesta propuesta) throws ConexionFallidaException;

    void update(Propuesta propuesta) throws ConexionFallidaException;

    Propuesta find(String titulo) throws ConexionFallidaException;

    List<Propuesta> findAll() throws ConexionFallidaException;
    
    void registrarAlumnoApropuesta(String nombrePropuesta , int idAlumno) throws ConexionFallidaException ;

}