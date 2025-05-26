package accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Actividad;
public interface ActividadDao {
    void create(Actividad actividad) throws ConexionFallidaException;
    void update(Actividad actividad) throws ConexionFallidaException;
    Actividad find(String nombreActividad)throws ConexionFallidaException;
    List<Actividad> findAll()throws ConexionFallidaException;
   
}