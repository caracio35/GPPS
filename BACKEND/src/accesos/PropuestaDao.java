package accesos;

import java.util.List;
import ar.edu.unrn.seminario.modelo.Propuesta;

public interface PropuestaDao {
    void create(Propuesta propuesta);
    void update(Propuesta propuesta);
    Propuesta find(Integer id);
    List<Propuesta> findAll();
}