package es.cic.bootcamp.individual06final.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TematicaException extends RuntimeException {

	public TematicaException() {
		super();
	}

	public TematicaException(String message, Throwable cause) {
		super(message, cause);
	}

	public TematicaException(String message) {
		super(message);
	}

	
	
}
