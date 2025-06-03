package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import accesos.ConvenioDAOJDBC;
import accesos.EntidadDAOJDBC;
import accesos.PropuestaDAOJDBC;
import accesos.RolDAOJDBC;
import accesos.RolDao;
import accesos.UsuarioDAOJDBC;
import accesos.UsuarioDao;
import ar.edu.unrn.seminario.dto.ActividadDTO;
import ar.edu.unrn.seminario.dto.AlumnoDTO;
import ar.edu.unrn.seminario.dto.EntidadDTO;
import ar.edu.unrn.seminario.dto.PersonaDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;
import ar.edu.unrn.seminario.modelo.Actividad;
import ar.edu.unrn.seminario.modelo.Convenio;
import ar.edu.unrn.seminario.modelo.Entidad;
import ar.edu.unrn.seminario.modelo.Persona;
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

	public void registrarUsuario(String username, String password, String email, String nombre, Integer codigoRol)
			throws ConexionFallidaException {
		Rol rol = rolDao.find(codigoRol);
		Persona persona = new Persona(nombre, null, null); // Asumiendo que el constructor de Persona acepta nombre,
															// apellido y dni
		Usuario usuario = new Usuario(username, password, email, rol, persona);
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
		Rol rol = null;
		PersonaDTO personaDTO = null;
		for (Usuario u : usuarios) {
			if (u.getPersona() == null) {
				Persona persona = u.getPersona();
				rol = u.getRol();
				personaDTO = new PersonaDTO(persona.getDni(), persona.getApellido(), persona.getDni());

				continue;
			}

			dtos.add(new UsuarioDTO(u.getUsuario(), u.getContrasena(), u.getEmail(), u.isActivo(), rol.getNombre(),
					personaDTO));

		}
		return dtos;
	}

	public UsuarioDTO obtenerUsuarioDTO(String username) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.find(username);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (usuario != null) {
			Rol rol = usuario.getRol();
			Persona persona = usuario.getPersona();
			PersonaDTO personaDTO = new PersonaDTO(persona.getDni(), persona.getApellido(), persona.getNombre());
			return new UsuarioDTO(usuario.getUsuario(), usuario.getContrasena(), usuario.getEmail(), usuario.isActivo(),
					rol.getNombre(), personaDTO);
		}
		return null;
	}

	@Override
	public Usuario obtenerUsuario(String username) {
		Usuario usuario = null;
		try {
			usuario = usuarioDao.find(username);
		} catch (ConexionFallidaException e) {
			e.printStackTrace();
		}
		if (usuario != null) {
			return usuario;
		}
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
	public RolDTO obtenerRolPorCodigo(Integer codigo) throws ConexionFallidaException {
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

	@Override
	public List<PropuestaDTO> obtenerTodasPropuestas() {
		List<PropuestaDTO> propuestasDTO = new ArrayList<>();

		PropuestaDAOJDBC dao = new PropuestaDAOJDBC();
		List<Propuesta> propuestas = null;

		try {
			propuestas = dao.findAll();
		} catch (ConexionFallidaException e) {
			e.printStackTrace();
		}

		for (Propuesta p : propuestas) {
			List<ActividadDTO> actividadesDTO = new ArrayList<>();
			for (Actividad actividad : p.getActividades()) {
				ActividadDTO actividadDTO = new ActividadDTO(
						actividad.getNombreActividad(),
						actividad.getHoras());
				actividadesDTO.add(actividadDTO);
			}

			String creador = null;
			Usuario usuario1 = p.getCreador();
			if (usuario1 != null) {
				creador = usuario1.getUsuario();
			}

			String alumno = null;
			Usuario usuario2 = p.getAlumno();
			if (usuario2 != null) {
				alumno = usuario2.getUsuario();
			}

			String tutor = null;
			Usuario usuario3 = p.getTutor();
			if (usuario3 != null) {
				tutor = usuario3.getUsuario();
			}

			String profesor = null;
			Usuario usuario4 = p.getProfesor();
			if (usuario4 != null) {
				profesor = usuario4.getUsuario();
			}

			PropuestaDTO propuestaDTO = null;
			try {
				propuestaDTO = new PropuestaDTO(
						p.getTitulo(),
						p.getDescripcion(),
						p.getAreaInteres(),
						p.getObjetivo(),
						p.getComentarios(),
						p.isAceptada(),
						creador,
						alumno,
						tutor,
						profesor,
						actividadesDTO);
			} catch (InvalidCantHorasExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			propuestasDTO.add(propuestaDTO);
		}
		return propuestasDTO;
	}

	@Override
	public PropuestaDTO obtenerPropuestaPorTitulo(String tituloProyecto) throws InvalidCantHorasExcepcion {

		PropuestaDAOJDBC dao = new PropuestaDAOJDBC();

		Propuesta propuesta = null;
		try {

			propuesta = dao.find(tituloProyecto);

		} catch (ConexionFallidaException e) {
			e.printStackTrace();
		}
		ArrayList<ActividadDTO> actividadsDTO = new ArrayList<>();
		for (Actividad actividad : propuesta.getActividades()) {
			ActividadDTO actividadDTO = new ActividadDTO(
					actividad.getNombreActividad(),
					actividad.getHoras());
			actividadsDTO.add(actividadDTO);
		}

		if (propuesta != null) {
			PropuestaDTO p = new PropuestaDTO(propuesta.getTitulo(), propuesta.getDescripcion(),
					propuesta.getAreaInteres(),
					propuesta.getObjetivo(), propuesta.getComentarios(), propuesta.isAceptada(),
					propuesta.getCreador().getUsuario(), null, null, null, actividadsDTO);

			return p;
		}
		return null;
	}

	@Override
	public EntidadDTO obtenerEntidad(int id) {

		EntidadDAOJDBC dao = new EntidadDAOJDBC();

		Entidad entidad = null;
		EntidadDTO entidadDTO = null;

		try {
			entidad = dao.find(id);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (entidad != null) {
			entidadDTO = new EntidadDTO(
					entidad.getNombre(),
					entidad.getTelefono(),
					entidad.getCorreo(),
					entidad.getCuit());
		}

		return entidadDTO;
	}

	@Override
	public void crearConvenio(String fechaGeneracion, String archivo, String tituloPropuesta, int idAlumno,
			int idProfesor) {

		ConvenioDAOJDBC dao = new ConvenioDAOJDBC();
		LocalDate fecha = parsearStringALocalDate(fechaGeneracion);
		Convenio convenio = new Convenio(fecha, false, archivo, tituloPropuesta, idAlumno, idProfesor);

		try {
			dao.create(convenio);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private LocalDate parsearStringALocalDate(String fechaStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(fechaStr, formatter);

	}

	@Override
	public void actualizarEstadoPropuesta(String id, int estado) throws ConexionFallidaException {
		// PropuestaDAOJDBC propuestaDao = new PropuestaDAOJDBC();
		// propuestaDao.find(id);
		// propuestaDao.actualizarEstadoPropuesta(id, estado);
	}

	@Override
	public EntidadDTO obtenerIdEntidad(String nombre) {

		EntidadDAOJDBC dao = new EntidadDAOJDBC();
		Entidad entidad = null;
		try {
			entidad = dao.find(nombre);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EntidadDTO entidadDao = new EntidadDTO(entidad.getNombre(), entidad.getTelefono(), entidad.getCorreo(),
				entidad.getCuit());
		entidadDao.setId(entidad.getId());

		return entidadDao;
	}

	@Override
	public void guardarPropuesta(PropuestaDTO propuestaDto) {

		PropuestaDAOJDBC dao = new PropuestaDAOJDBC();

		List<Actividad> actividades = new ArrayList<>();
		for (ActividadDTO actDTO : propuestaDto.getActividades()) {
			Actividad actividad = new Actividad(actDTO.getNombreActividad(), actDTO.getHoras());
			actividades.add(actividad);
		}

		Propuesta propuesta = null;
		Usuario creador = obtenerUsuario(propuestaDto.getCreador());
		Usuario alumno = obtenerUsuario(propuestaDto.getAlumno());
		Usuario tutor = obtenerUsuario(propuestaDto.getTutor());
		Usuario profesor = obtenerUsuario(propuestaDto.getProfesor());

		try {
			propuesta = new Propuesta(
					propuestaDto.getTitulo(),
					propuestaDto.getDescripcion(),
					propuestaDto.getAreaInteres(),
					propuestaDto.getObjetivo(),
					propuestaDto.getComentarios(),
					propuestaDto.isAceptada(), creador, alumno, tutor, profesor,
					actividades);

		} catch (InvalidCantHorasExcepcion e) {
			e.printStackTrace();

		}

		try {
			dao.create(propuesta);
		} catch (ConexionFallidaException e) {
			e.printStackTrace();

		}
	}

	@Override
	public void registrarInscripcionAlumno(int idAlumno, String nombreDePropuesta) {

		PropuestaDAOJDBC dao = new PropuestaDAOJDBC();
		try {
			dao.registrarAlumnoApropuesta(nombreDePropuesta, idAlumno);
		} catch (ConexionFallidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<UsuarioSimplificadoDTO> obtenerUsuariosSimplificados() {
		List<UsuarioSimplificadoDTO> dtos = new ArrayList<>();
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDao.findAll();
		} catch (ConexionFallidaException e) {
			System.out.println("No se pudo conectar: " + e.getMessage());
		}
		if (usuarios != null) {
			for (Usuario u : usuarios) {
				String nombre = u.getPersona() != null ? u.getPersona().getNombre() : "";
				String apellido = u.getPersona() != null ? u.getPersona().getApellido() : "";
				int dni = 0;
				if (u.getPersona() != null && u.getPersona().getDni() != null) {
					try {
						dni = Integer.parseInt(u.getPersona().getDni());
					} catch (NumberFormatException e) {
					}
				}
				dtos.add(new UsuarioSimplificadoDTO(
						nombre,
						apellido,
						u.getEmail(),
						u.getRol().getNombre(),
						dni,
						u.getUsuario()));
			}
		}
		return dtos;
	}

	@Override
	public AlumnoDTO obtenerIdAlumno(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlumnoDTO obtenerAlumno(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarUsuario(String username, String password, String email, Integer codigoRol, Persona p)
			throws ConexionFallidaException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registrarUsuario'");
	}
}
