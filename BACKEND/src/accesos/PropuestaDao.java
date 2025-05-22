package accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaExeption;
import ar.edu.unrn.seminario.modelo.Propuesta;

public interface PropuestaDao {
    void create(Propuesta propuesta) throws ConexionFallidaExeption;

    void update(Propuesta propuesta) throws ConexionFallidaExeption;

    Propuesta find(Integer id) throws ConexionFallidaExeption;

    List<Propuesta> findAll() throws ConexionFallidaExeption;

}