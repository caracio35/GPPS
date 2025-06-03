package ar.edu.unrn.seminario.modelo;

public class Usuario {
	private String usuario;
	private String contrasena;
	private String email;
	private boolean activo;
	private Rol rol; // id de rol
	private Persona persona; // dni

	public Usuario(String usuario, String contrasena, String email, Rol rol, Persona persona) {

		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = email;
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

	public Rol getRol() {
		return rol;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public String obtenerEstado() {
		return isActivo() ? "ACTIVO" : "INACTIVO";
	}

	public void activar() {
		if (!isActivo())
			this.activo = true;
	}

	public void desactivar() {
		if (isActivo())
			this.activo = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public Persona getPersona() {
		return persona;
	}

}
