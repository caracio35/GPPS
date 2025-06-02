package ar.edu.unrn.seminario.exception;

import java.sql.SQLException;

public class DuplicadaException extends SQLException{
	public DuplicadaException(String mensaje) {
		super(mensaje);
	}
	public DuplicadaException() {
		super("Primary Key duplicada");
	}
}
//utilizar cuando ya hay una propuesta igual o cosas iguales