package ar.edu.unrn.seminario.dto;

public class UsuarioSimplificadoDTO {
	private String nombre;
	private String apellido;
	private String email;
	private String rol;
	private int dni;
	

	public UsuarioSimplificadoDTO(String nombre,String Apellido, String email, String rol, String estado) {
		super();
		this.nombre = nombre;
		this.apellido=apellido;
		this.email = email;
		this.rol = rol;
		this.dni=dni;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}

