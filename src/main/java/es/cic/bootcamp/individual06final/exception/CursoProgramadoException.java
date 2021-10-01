package es.cic.bootcamp.individual06final.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CursoProgramadoException extends RuntimeException {

	public CursoProgramadoException() {
		super();
	}

	public CursoProgramadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CursoProgramadoException(String message) {
		super(message);
	}

	
	
}
