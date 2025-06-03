package ar.edu.unrn.seminario.modelo;

public class Actividad {
	private String nombreActividad;
	private int horas;

	public Actividad(String nombreActividad, int horas) {

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
		return "Actividad{" +
				"id=" +
				", nombreActividad='" + nombreActividad + '\'' +
				", horas=" + horas +
				'}';
	}

	
}
