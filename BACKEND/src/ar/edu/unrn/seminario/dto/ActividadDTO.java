package ar.edu.unrn.seminario.dto;

public class ActividadDTO {

    private String nombreActividad;
    private int horas;

    public ActividadDTO(String nombreActividad, int horas) {

        this.nombreActividad = nombreActividad;
        this.horas = horas;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "ActividadDTO{" +
                "id=" +
                ", nombreActividad='" + nombreActividad + '\'' +
                ", horas=" + horas +
                ", propuestaId=" +
                '}';
    }
}
