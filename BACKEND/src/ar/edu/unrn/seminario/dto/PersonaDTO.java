package ar.edu.unrn.seminario.dto;

public class PersonaDTO {
	private String dni;
	private String nombre;
	private String apellido;

	// Constructor, getters y setters...
	public PersonaDTO(String dni, String nombre, String apellido) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "PersonaDTO{" +
				"dni='" + dni + '\'' +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				'}';
	}
}
