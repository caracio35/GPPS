package ar.edu.unrn.seminario.api;

import java.util.List;

import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioSimplificadoDTO;
import ar.edu.unrn.seminario.dto.PropuestaDTO;

public interface IApi {

	void registrarUsuario(String username, String password, String email, String nombre, Integer rol);// recuperar el usuario almacenado

	void eliminarUsuario(String username); // eliminar el usuario almacenado

	List<RolDTO> obtenerRoles(); // recuperar todos los roles

	List<RolDTO> obtenerRolesActivos(); // recuperar todos los roles activos

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.
	
	void crearPropuesta(PropuestaDTO propuesta) throws Exception; // crear la propuesta

	List<PropuestaDTO> listarPropuestas() throws Exception; // listar las propuestas
    
	PropuestaDTO obtenerPropuesta(int propuestaId) throws Exception; // obtener una propuesta por su ID
    
	void actualizarPropuesta(PropuestaDTO propuesta) throws Exception; // actualizar una propuesta
    
	void aceptarPropuesta(int propuestaId, String comentario) throws Exception; // aceptar una propuesta
    
	void rechazarPropuesta(int propuestaId, String comentario) throws Exception; // rechazar una propuesta
    
    UsuarioSimplificadoDTO obtenerUsuario(String nombreUsuario) throws Exception; // obtener un usuario por su nombre de usuario
}
