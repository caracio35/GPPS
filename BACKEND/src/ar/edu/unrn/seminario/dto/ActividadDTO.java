package ar.edu.unrn.seminario.dto;

public class ActividadDTO {
    private String nombre;
    private int horas;

    public ActividadDTO(String nombre, int horas) {
        this.nombre = nombre;
        this.horas = horas;
    }

    public ActividadDTO() {
        //TODO Auto-generated constructor stub
    }

    // Getters
    public String getnombre() {
        return nombre;
    }

    public int getHoras() {
        return horas;
    }

    // Setters
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
