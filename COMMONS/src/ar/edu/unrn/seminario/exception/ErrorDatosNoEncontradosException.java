package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class ErrorDatosNoEncontradosException extends SQLException {
	public ErrorDatosNoEncontradosException(String mensaje) {
		super(mensaje);
	}
	public ErrorDatosNoEncontradosException() {
		super("Datos no encontrados en la base de datos");
	}
}


