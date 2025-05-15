package accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Actividad;
public interface ActividadDao {
    void create(Actividad actividad);
    void update(Actividad actividad);
    Actividad find(Integer id);
    List<Actividad> findAll();
    List<Actividad> findByPropuesta(Integer propuestaId);
    String getNombre();
}