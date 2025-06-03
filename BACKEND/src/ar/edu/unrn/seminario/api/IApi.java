package ar.edu.unrn.seminario.api;

import java.util.List;

import ar.edu.unrn.seminario.dto.AlumnoDTO;
import ar.edu.unrn.seminario.dto.EntidadDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;
import ar.edu.unrn.seminario.exception.ConexionFallidaException;
import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface IApi {

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol)
			throws ConexionFallidaException;

	Usuario obtenerUsuario(String username);

	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo) throws ConexionFallidaException; // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	List<PropuestaDTO> obtenerTodasPropuestas();

	PropuestaDTO obtenerPropuestaPorTitulo(String tituloProyecto) throws InvalidCantHorasExcepcion;

	void crearConvenio(String fechaGeneracion, String archivo, String tituloPropuesta, int idAlumno, int idProfesor);

	EntidadDTO obtenerEntidad(int id);

	EntidadDTO obtenerIdEntidad(String nombre);

	AlumnoDTO obtenerAlumno(int id);

	AlumnoDTO obtenerIdAlumno(String nombre);

	void actualizarEstadoPropuesta(String id, int i) throws ConexionFallidaException;

	void guardarPropuesta(PropuestaDTO propuesta);

	void registrarInscripcionAlumno(int idAlumno, String nombreDePropuesta);

	List<UsuarioSimplificadoDTO> obtenerUsuariosSimplificados();
}
