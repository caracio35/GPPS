package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

public class PropuestaDTO {
    private String titulo;
    private String areaInteres;
    private String objetivo;
    private String descripcion;
    private int aceptada; // 0 = no aceptada, 1 = aceptada ,-1 = rechazada
    private String comentarios;
    private List<ActividadDTO> actividades;
    private int idAlumno; // FK a Alumno
    private int idEntidad; // FK a Entidad
    private int idProfesoPrincipal;

    public PropuestaDTO(String titulo, String areaInteres, String objetivo, String descripcion, int aceptada,
            String comentarios, int idAlumno, int idEntidad, int idProfesoPrincipal) {
        this.titulo = titulo;
        this.areaInteres = areaInteres;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
        this.aceptada = aceptada;
        this.comentarios = comentarios;
        this.idAlumno = idAlumno;
        this.idEntidad = idEntidad;
        this.idProfesoPrincipal = idProfesoPrincipal;
        this.actividades = new ArrayList<>(); // Inicializo la lista vacía
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAreaInteres() {
        return areaInteres;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int isAceptada() {
        return aceptada;
    }

    public String getComentarios() {
        return comentarios;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public int getIdProfesoPrincipal() {
        return idProfesoPrincipal;
    }

    public List<ActividadDTO> getActividades() {
        return new ArrayList<>(actividades);
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAreaInteres(String areaInteres) {
        this.areaInteres = areaInteres;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAceptada(int aceptada) {
        this.aceptada = aceptada;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public void setIdProfesoPrincipal(int idProfesoPrincipal) {
        this.idProfesoPrincipal = idProfesoPrincipal;
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
                ", areaInteres='" + areaInteres + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aceptada=" + aceptada +
                ", comentarios='" + comentarios + '\'' +
                ", idAlumno=" + idAlumno +
                ", idEntidad=" + idEntidad +
                ", idProfesoPrincipal=" + idProfesoPrincipal +
                ", actividades=" + actividades +
                '}';
    }
}
