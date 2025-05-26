package ar.edu.unrn.seminario.modelo;

import java.util.Date;

public class Convenio {
	
	    private int idConvenio;
	    private Date fechaGeneracion;
	    private String estado;
	    private String archivo; 
	    private Propuesta propuesta; 


	  
	    public Convenio(int idConvenio, Date fechaGeneracion, String estado, String archivo, Propuesta propuesta) {
	        this.idConvenio = idConvenio;
	        this.fechaGeneracion = fechaGeneracion;
	        this.estado = estado;
	        this.archivo = archivo;
	        this.propuesta = propuesta;
	     
	    }

	    // Getters y Setters
	    public int getIdConvenio() {
	        return idConvenio;
	    }

	    public void setIdConvenio(int idConvenio) {
	        this.idConvenio = idConvenio;
	    }

	    public Date getFechaGeneracion() {
	        return fechaGeneracion;
	    }

	    public void setFechaGeneracion(Date fechaGeneracion) {
	        this.fechaGeneracion = fechaGeneracion;
	    }

	    public String getEstado() {
	        return estado;
	    }

	    public void setEstado(String estado) {
	        this.estado = estado;
	    }

	    public String getArchivo() {
	        return archivo;
	    }

	    public void setArchivo(String archivo) {
	        this.archivo = archivo;
	    }
	    public Propuesta getPropuesta() {
	    	return this.propuesta;
	    }

	    // Método para mostrar el convenio (opcional)
	    @Override
	    public String toString() {
	        return "Convenio{" +
	                "idConvenio=" + idConvenio +
	                ", fechaGeneracion=" + fechaGeneracion +
	                ", estado='" + estado + '\'' +
	                ", archivo='" + archivo + '\'' +
	                '}';
	    }
}
