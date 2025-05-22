package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class ConexionFallidaExeption extends SQLException {
	public ConexionFallidaExeption(String mensaje) {
		super(mensaje);
	}
	public ConexionFallidaExeption() {
		super("Coneccion fallida con la base de datos");
}
}