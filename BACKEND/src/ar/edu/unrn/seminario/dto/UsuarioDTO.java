package ar.edu.unrn.seminario.dto;

public class UsuarioDTO {
	private String usuario;
	private String contrasena;
	private String email;
	private boolean activo;
	private String rol;
	private PersonaDTO persona;

	public UsuarioDTO(String usuario, String contrasena, String email, boolean activo, String rol, PersonaDTO persona) {
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = email;
		this.activo = activo;
		this.rol = rol;
		this.persona = persona;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return persona + " (" + usuario + ")";
	}
}
