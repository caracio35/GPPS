package ar.edu.unrn.seminario.modelo;

public class Actividad {
	
	    private String nombrePropuesta; 
	    private String nombre;
	    private int horas;

	    // Constructor con parámetros
	    public Actividad(String nombre, int horas, String nombrePropuesta) {
	        this.nombre = nombre;
	        this.horas = horas;
	        this.nombrePropuesta = nombrePropuesta;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public int getHoras() {
	        return horas;
	    }

	    public void setHoras(int horas) {
	        this.horas = horas;
	    }

	    public String getNombrePropuesta() {
	        return nombrePropuesta;
	    }

	    public void setNombrePropuesta(String nombrePropuesta) {
	        this.nombrePropuesta = nombrePropuesta;
	    }

	    @Override
	    public String toString() {
	        return "Actividad{" +
	                "nombre='" + nombre + '\'' +
	                ", horas=" + horas +
	                ", nombrePropuesta='" + nombrePropuesta + '\'' +
	                '}';
	    }
  

}
