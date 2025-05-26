package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

public class Propuesta {
	
	    private String titulo;
	    private String areaInteres;
	    private String objetivo;
	    private String descripcion;
	    private boolean aceptada;
	    private String comentarios;
	    private List<Actividad> actividades ; 
	    private int idAlumno; // FK a Alumno
	    private int idEntidad; // FK a Entidad
	    private int idProfesoPrincipal ; 


	    // Constructor con todos los campos
	    public Propuesta(String titulo, String areaInteres, String objetivo, String descripcion,
	                     String comentarios, int idAlumno, boolean aceptada ,int idEntidad , List<Actividad> lista , int idProfesor ) {
	        
	        this.titulo = titulo;
	        this.areaInteres = areaInteres;
	        this.objetivo = objetivo;
	        this.descripcion = descripcion;
	        this.aceptada = aceptada;
	        this.comentarios = comentarios;
	        this.idAlumno = idAlumno;
	        this.idEntidad = idEntidad;
	        this.actividades = lista ; 
	        this.idProfesoPrincipal = idProfesor;
	    }

	    
	    public String getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public String getAreaInteres() {
	        return areaInteres;
	    }

	    public void setAreaInteres(String areaInteres) {
	        this.areaInteres = areaInteres;
	    }

	    public String getObjetivo() {
	        return objetivo;
	    }
	    public List<Actividad> getActividades() {
	        return actividades;
	    }

	    public void setActividades(List<Actividad> actividades) {
	        this.actividades = actividades;
	    }
	    public void setObjetivo(String objetivo) {
	        this.objetivo = objetivo;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public boolean isAceptada() {
	        return aceptada;
	    }

	    public void setAceptada(boolean aceptada) {
	        this.aceptada = aceptada;
	    }

	    public String getComentarios() {
	        return comentarios;
	    }

	    public void setComentarios(String comentarios) {
	        this.comentarios = comentarios;
	    }

	    public int getIdAlumno() {
	        return idAlumno;
	    }
	    public int getIdPorfesor() {
	    	return idProfesoPrincipal ; 
	    }

	    public void setIdAlumno(int idAlumno) {
	        this.idAlumno = idAlumno;
	    }

	    public int getIdEntidad() {
	        return idEntidad;
	    }

	    public void setIdEntidad(int idEntidad) {
	        this.idEntidad = idEntidad;
	    }
	    public void agregarActividad(Actividad actividad) {
	        if (this.actividades == null) {
	            this.actividades = new ArrayList<>();
	        }
	        this.actividades.add(actividad);
	    }

	    @Override
	    public String toString() {
	        return "Propuesta{" +
	                ", titulo='" + titulo + '\'' +
	                ", areaInteres='" + areaInteres + '\'' +
	                ", objetivo='" + objetivo + '\'' +
	                ", descripcion='" + descripcion + '\'' +
	                ", aceptada=" + aceptada +
	                ", comentarios='" + comentarios + '\'' +
	                ", idAlumno=" + idAlumno +
	                ", idEntidad=" + idEntidad +
	                ", actividades=" + actividades +
	                '}';
	    }
	
}
