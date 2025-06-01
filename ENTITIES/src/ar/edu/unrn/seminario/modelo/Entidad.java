package ar.edu.unrn.seminario.modelo;

public class Entidad {
	
	private int id ;
	private String nombre;
    private String telefono;
    private String correo;
    private String cuit;

    public Entidad(String nombre, String telefono, String correo, String cuit) {
    	this.id = 0 ;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.cuit = cuit;
    }

    public void setId(int id) {
    	this.id = id ;
    }
    public int getId() {
    	return id;
    }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getCuit() { return cuit; }

    
}
