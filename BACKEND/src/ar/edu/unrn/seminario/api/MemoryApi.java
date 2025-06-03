package ar.edu.unrn.seminario.api;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.dto.AlumnoDTO;
import ar.edu.unrn.seminario.dto.EntidadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.modelo.Persona;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class MemoryApi implements IApi {

	private ArrayList<Rol> roles = new ArrayList();
	private ArrayList<Usuario> usuarios = new ArrayList<>();

	public MemoryApi() {

		// datos iniciales
		this.roles.add(new Rol(1, "ADMIN"));
		this.roles.add(new Rol(2, "ESTUDIANTE"));
		this.roles.add(new Rol(3, "INVITADO"));

	}

	@Override
	public List<UsuarioDTO> obtenerUsuarios() {
		List<UsuarioDTO> dtos = new ArrayList<>();
		//
		return dtos;
	}

	@Override
	public Usuario obtenerUsuario(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarUsuario(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RolDTO> obtenerRoles() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public List<RolDTO> obtenerRolesActivos() {
		List<RolDTO> dtos = new ArrayList<>();
		for (Rol r : this.roles) {
			if (r.isActivo())
				dtos.add(new RolDTO(r.getCodigo(), r.getNombre()));
		}
		return dtos;
	}

	@Override
	public void guardarRol(Integer codigo, String descripcion, boolean estado) {
		// TODO Auto-generated method stub
		Rol rol = new Rol(codigo, descripcion);
		this.roles.add(rol);
	}

	@Override
	public RolDTO obtenerRolPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
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
	public void activarUsuario(String usuario) {
		Usuario user = this.buscarUsuario(usuario);
		user.activar();
	}

	@Override
	public void desactivarUsuario(String usuario) {
		// TODO: desactivar usuario
	}

	private Rol buscarRol(Integer codigo) {
		for (Rol rol : roles) {
			if (rol.getCodigo().equals(codigo))
				return rol;
		}
		return null;
	}

	private Usuario buscarUsuario(String usuario) {
		for (Usuario user : usuarios) {
			if (user.getUsuario().equals(usuario))
				return user;
		}
		return null;
	}

	@Override
	public PropuestaDTO obtenerPropuestaPorTitulo(String tituloProyecto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropuestaDTO> obtenerTodasPropuestas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadDTO obtenerEntidad(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlumnoDTO obtenerAlumno(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearConvenio(String fechaGeneracion, String archivo, String tituloPropuesta, int idAlumno,
			int idProfesor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualizarEstadoPropuesta(String id, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'actualizarEstadoPropuesta'");
	}

	@Override
	public EntidadDTO obtenerIdEntidad(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlumnoDTO obtenerIdAlumno(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarPropuesta(PropuestaDTO propuesta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registrarInscripcionAlumno(int idAlumno, String nombreDePropuesta) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UsuarioSimplificadoDTO> obtenerUsuariosSimplificados() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'obtenerUsuariosSimplificados'");
	}

	@Override
	public void registrarUsuario(String username, String password, String email, Integer codigoRol, Persona p)
			throws ConexionFallidaException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'registrarUsuario'");
	}
}
