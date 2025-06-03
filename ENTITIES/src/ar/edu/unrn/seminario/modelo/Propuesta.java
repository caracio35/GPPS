package ar.edu.unrn.seminario.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.InvalidCantHorasExcepcion;

public class Propuesta {
	private String titulo;
	private String descripcion;
	private String areaInteres;
	private String objetivo;
	private String comentarios;
	private int aceptada; // 0 para no aceptada, 1 para aceptada, -1 para rechazada
	private Usuario creador; // usuario.usuario
	private Usuario alumno; // usuario.usuario
	private Usuario tutor; // usuario.usuario
	private Usuario profesor; // usuario.usuario
	private List<Actividad> actividades = new ArrayList<>();

	// Constructor con todos los campos
	public Propuesta(String titulo, String descripcion, String areaInteres, String objetivo, String comentarios,
			int aceptada, Usuario creador, Usuario alumno, Usuario tutor, Usuario profesor,
			List<Actividad> actividades)
			throws InvalidCantHorasExcepcion {
		int totalHoras = 0;
		if (actividades != null) {
			for (Actividad a : actividades) {
				totalHoras += a.getHoras();
			}
		}
		if (totalHoras < 100 || totalHoras > 200) {
			throw new InvalidCantHorasExcepcion(
					"La propuesta debe tener entre 100 y 200 horas. Total: " + totalHoras);
		}

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
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public int isAceptada() {
		return aceptada;
	}

	public void setAceptada(int aceptada) {
		this.aceptada = aceptada;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public Usuario getAlumno() {
		return alumno;
	}

	public void setAlumno(Usuario alumno) {
		this.alumno = alumno;
	}

	public Usuario getTutor() {
		return tutor;
	}

	public void setTutor(Usuario tutor) {
		this.tutor = tutor;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public void agregarActividad(Actividad actividad) {
		actividades.add(actividad);
	}

	@Override
	public String toString() {
		return "Propuesta{" +
				", titulo='" + titulo + '\'' +
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
