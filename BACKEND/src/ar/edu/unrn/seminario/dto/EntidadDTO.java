package ar.edu.unrn.seminario.dto;

public class EntidadDTO {
	
	private String nombre;
    private String telefono;
    private String correo;
    private String cuit;

    public EntidadDTO(String nombre, String telefono, String correo, String cuit) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.cuit = cuit;
    }

    
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getCuit() { return cuit; }

}
