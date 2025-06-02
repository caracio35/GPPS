package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;

public class PropuestaDTO {

    private String titulo;
    private String descripcion;
    private String areaInteres;
    private String objetivo;
    private String comentarios;
    private Boolean aceptada;
    private String creador;
    private String alumno;
    private String tutor;
    private String profesor;
    private List<ActividadDTO> actividades;

    public PropuestaDTO(String titulo, String descripcion, String areaInteres, String objetivo, String comentarios,
            Boolean aceptada, String creador, String alumno, String tutor, String profesor,
            List<ActividadDTO> actividades)
            throws InvalidCantHorasExcepcion {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.areaInteres = areaInteres;
        this.objetivo = objetivo;
        this.comentarios = comentarios;
        this.aceptada = aceptada;
        this.creador = creador;
        this.alumno = alumno;
        this.tutor = tutor;
        this.profesor = profesor;
        this.actividades = actividades; // Inicializo la lista vacía
        int totalHoras = 0;
        if (actividades != null) {
            for (ActividadDTO actividad : actividades) {
                totalHoras += actividad.getHoras();
            }
        }
        if (totalHoras < 100 || totalHoras > 200) {
            throw new InvalidCantHorasExcepcion("La propuesta debe tener entre 100 y 200 horas. Total: " + totalHoras);
        }
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAreaInteres() {
        return areaInteres;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public Boolean isAceptada() {
        return aceptada;
    }

    public String getCreador() {
        return creador;
    }

    public String getAlumno() {
        return alumno;
    }

    public String getTutor() {
        return tutor;
    }

    public String getProfesor() {
        return profesor;
    }

    public List<ActividadDTO> getActividades() {
        return new ArrayList<>(actividades);
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAreaInteres(String areaInteres) {
        this.areaInteres = areaInteres;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setActividades(List<ActividadDTO> actividades) {
        this.actividades = new ArrayList<>(actividades);
    }

    public void agregarActividad(ActividadDTO actividad) {
        this.actividades.add(actividad);
    }

    @Override
    public String toString() {
        return "PropuestaDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", areaInteres='" + areaInteres + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", aceptada=" + aceptada +
                ", creador='" + creador + '\'' +
                ", alumno='" + alumno + '\'' +
                ", tutor='" + tutor + '\'' +
                ", profesor='" + profesor + '\'' +
                ", actividades=" + actividades +
                '}';
    }
}
