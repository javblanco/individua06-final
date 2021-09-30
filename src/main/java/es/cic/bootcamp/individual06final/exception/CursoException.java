package es.cic.bootcamp.individual06final.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CursoException extends RuntimeException {

	public CursoException() {
		super();
	}

	public CursoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CursoException(String message) {
		super(message);
	}

	
}
