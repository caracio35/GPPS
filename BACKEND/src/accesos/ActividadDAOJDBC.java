package accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Actividad;

public class ActividadDAOJDBC implements ActividadDao {

	@Override
	public void create(Actividad actividad) throws ConexionFallidaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Actividad actividad) throws ConexionFallidaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Actividad find(String nombreActividad) throws ConexionFallidaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Actividad> findAll() throws ConexionFallidaException {
		// TODO Auto-generated method stub
		return null;
	}

	
}