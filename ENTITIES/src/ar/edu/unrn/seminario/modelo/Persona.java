package ar.edu.unrn.seminario.modelo;

public abstract class Persona {
	
	private String nombre;
	private  String apellido;
	private  String dni;
	private  String correo;

	    // Constructor
	    public Persona(String nombre, String apellido, String dni, String correo) {
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.dni = dni;
	        this.correo = correo;
	    }

	    

		// Getters y Setters
	    public String getNombre() { return nombre; }
	    public String getApellido() { return apellido; }
	    public String getDni() { return dni; }
	    public String getCorreo() { return correo; }
	}

