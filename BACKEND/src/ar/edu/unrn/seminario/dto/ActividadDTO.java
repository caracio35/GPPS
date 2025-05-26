package ar.edu.unrn.seminario.dto;

public class ActividadDTO {
	
	private String nombrePropuesta;
    private String nombre;
    private int horas;

    public ActividadDTO(String nombrePropuesta , int horas, String nombre) {
    	
    	this.nombrePropuesta = nombrePropuesta ;
        this.nombre = nombre;
        this.horas = horas;
    }

    
	public String getnombrePropuesta() {
        return nombrePropuesta;
    }
    
    public void setnombrePropuesta(String nombre) {
        this.nombrePropuesta = nombre;
    }

    public String getnombre() {
        return nombre;
    }

    public int getHoras() {
        return horas;
    }

  
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "ActividadDTO{" +
                "nombre='" + nombre + '\'' +
                ", horas=" + horas +
                '}';
    }
}
