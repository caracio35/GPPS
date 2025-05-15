package ar.edu.unrn.seminario.dto;

import java.util.List;
import java.util.ArrayList;

public class PropuestaDTO {
    private String titulo;
    private String areaInteres;
    private String objetivo;
    private String descripcion;
    private int dniAutor;
    private int totalHoras;
    private List<ActividadDTO> actividades;

    public PropuestaDTO(String titulo, String areaInteres, String objetivo, String descripcion, 
                       int dniAutor, int totalHoras) {
        this.titulo = titulo;
        this.areaInteres = areaInteres;
        this.objetivo = objetivo;
        this.descripcion = descripcion;
        this.dniAutor = dniAutor;
        this.totalHoras = totalHoras;
        this.actividades = new ArrayList<>();
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

    public int getDniAutor() {
        return dniAutor;
    }

    public int getTotalHoras() {
        return totalHoras;
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

    public void setDniAutor(int dniAutor) {
        this.dniAutor = dniAutor;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
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
                ", dniAutor=" + dniAutor +
                ", totalHoras=" + totalHoras +
                ", actividades=" + actividades +
                '}';
    }
}
