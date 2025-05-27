package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.Date;

public class Convenio {
	
	    
	    private LocalDate fechaGenerado;
	    private boolean estado;
	    private String archivo; 
	    private String propuesta; 
	    private int idAlumno; 
	    private int idProfesor;


	  
	    public Convenio( LocalDate fechaGeneracion, boolean estado, String archivo, String propuesta , int idAlumno , int idProfesor) {
	        
	        this.fechaGenerado = fechaGeneracion;
	        this.estado = estado;
	        this.archivo = archivo;
	        this.propuesta = propuesta;
	        this.idAlumno = idAlumno ;
	        this.idProfesor = idProfesor ; 
	     
	    }

	    public LocalDate getFechaGeneracion() {
	        return fechaGenerado;
	    }
	    public int getIdAlumno() {
	    	return idAlumno ; 
	    }
	    public int getIdProfesor() {
	    	return idProfesor; 
	    }
	    public void setFechaGeneracion(LocalDate fechaGeneracion) {
	        this.fechaGenerado = fechaGeneracion;
	    }

	    public boolean getEstado() {
	        return estado;
	    }

	    public void setEstado(boolean estado) {
	        this.estado = estado;
	    }

	    public String getArchivo() {
	        return archivo;
	    }

	    public void setArchivo(String archivo) {
	        this.archivo = archivo;
	    }
	    public String getPropuesta() {
	    	return this.propuesta;
	    }

	    // Método para mostrar el convenio (opcional)
	    @Override
	    public String toString() {
	        return "Convenio{" +
	                "idConvenio=" + 
	                ", fechaGeneracion=" + fechaGenerado +
	                ", estado='" + estado + '\'' +
	                ", archivo='" + archivo + '\'' +
	                '}';
	    }
}
