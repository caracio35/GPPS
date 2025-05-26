package ar.edu.unrn.seminario.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accesos.PropuestaDAOJDBC;
import accesos.RolDAOJDBC;
import accesos.RolDao;
import accesos.UsuarioDAOJDBC;
import accesos.UsuarioDao;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Propuesta;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class PersistenceApi implements IApi {

	private RolDao rolDao;
	private UsuarioDao usuarioDao;
	private List<Propuesta> propuestas;
	private ArrayList<PropuestaDTO> propuestasDTO;

	public PersistenceApi() {
		rolDao = new RolDAOJDBC();
		usuarioDao = new UsuarioDAOJDBC();
	}

	@Override
	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol) {
		Rol rol = rolDao.find(codigoRol);
		Usuario usuario = new Usuario(username, password, nombre, email, rol);
		try {
			this.usuarioDao.create(usuario);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDao.findAll();
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Usuario u : usuarios) {
			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getNombre(), u.getEmail(),
					u.getRol().getNombre(), u.isActivo(), u.obtenerEstado()));
		}
		return dtos;
	}

	@Override
	public UsuarioDTO obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.find(username);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (usuario != null) {
			try {
				usuarioDao.remove(usuario);
			} catch (ConexionFallidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<Rol> roles = null;
		try {
			roles = rolDao.findAll();
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<RolDTO> rolesDTO = new ArrayList<>(0);
		for (Rol rol : roles) {
			rolesDTO.add(new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo()));
		}
		return rolesDTO;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub

	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		Rol rol = rolDao.find(codigo);
		RolDTO rolDTO = new RolDTO(rol.getCodigo(), rol.getNombre(), rol.isActivo());
		return rolDTO;
	}

	@Override
	public void activarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desactivarRol(Integer codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activarUsuario(String username) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.find(username);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (usuario != null) {
			usuario.activar();
			try {
				usuarioDao.update(usuario);
			} catch (ConexionFallidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void desactivarUsuario(String username) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.find(username);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (usuario != null) {
			usuario.desactivar();
			try {
				usuarioDao.update(usuario);
			} catch (ConexionFallidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void guardarPropuesta(PropuestaDTO propuesta, List<ActividadDTO> actividades) throws SQLException {
		PropuestaDAOJDBC dao = new PropuestaDAOJDBC();
		try {
			dao.insertarPropuestaConActividades(propuesta, actividades);
		} catch (ConexionFallidaException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<PropuestaDTO> ObtenerTodasPropuestas() {

		    List<PropuestaDTO> propuestasDTO = new ArrayList<>();

		    // DAO que trae todas las propuestas
		    PropuestaDAOJDBC dao = new PropuestaDAOJDBC();
		    
		    List<Propuesta> propuestas = null;
			try {
				propuestas = dao.findAll();
			} catch (ConexionFallidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		    for (Propuesta p : propuestas) {
		        // Crear el DTO a partir de la propuesta
		        PropuestaDTO propuestaDTO = new PropuestaDTO(
		               p.getTitulo() , p.getAreaInteres() , p.getObjetivo() , p.getDescripcion() , 
		               p.isAceptada() , p.getComentarios() ,  p.getIdAlumno(),
		               p.getIdEntidad(), p.getIdPorfesor() 
		        );
		        // Pasar las actividades de Propuesta a PropuestaDTO
		        for (Actividad actividad : p.getActividades()) {
		            ActividadDTO actividadDTO = new ActividadDTO(
		                    actividad.getNombre(),
		                    actividad.getHoras(),
		                    actividad.getNombrePropuesta() 
		            );
		            propuestaDTO.agregarActividad(actividadDTO);
		        }
		    }

		    return propuestasDTO;
		}

	@Override
	public void crearConvinio(String nombre_propuesta, String nombre_alumno, String nombre_tutor,
			String fecha_convenio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PropuestaDTO obtenerPropuestaPorTitulo(String tituloProyecto) {
		
		  PropuestaDAOJDBC dao = new PropuestaDAOJDBC();
		  
		  Propuesta propuesta = null;
		try {
			
			propuesta = dao.find(tituloProyecto);
			
		} catch (ConexionFallidaException e) {
			e.printStackTrace();
		} 

		    if (propuesta != null) {
		        PropuestaDTO propuestaDTO = new PropuestaDTO(
		                propuesta.getTitulo(),
		                propuesta.getAreaInteres(),
		                propuesta.getObjetivo(),
		                propuesta.getDescripcion(),
		                propuesta.isAceptada(),
		                propuesta.getComentarios(),
		                propuesta.getIdAlumno(),
		                propuesta.getIdEntidad(),
		                propuesta.getIdPorfesor()
		        );

		        // Agregar las actividades (convertidas a DTO)
		        for (Actividad actividad : propuesta.getActividades()) {
		            ActividadDTO actividadDTO = new ActividadDTO(
		                    actividad.getNombre(),
		                    actividad.getHoras(),
		                    actividad.getNombrePropuesta()
		            );
		            propuestaDTO.agregarActividad(actividadDTO);
		        }

		        return propuestaDTO;
		    }

		    return null;
		}

}
