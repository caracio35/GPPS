package ar.edu.unrn.seminario.exception;

public class CampoVacioException extends Exception {
    public CampoVacioException(String mensaje) {
        super(mensaje);
    }
    public CampoVacioException() {
    	super("Campo vacio");
    }
}