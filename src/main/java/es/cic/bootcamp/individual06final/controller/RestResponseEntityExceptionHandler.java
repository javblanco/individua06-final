package es.cic.bootcamp.individual06final.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.exception.TematicaException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value 
      = {TransactionSystemException .class})
    protected ResponseEntity<Object> handleValidaciones(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "No se han introducido los datos del registro correctamente.";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value 
      = {TematicaException.class})
    protected ResponseEntity<Object> handleTematicaException(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse =  ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value 
      = {CursoException.class})
    protected ResponseEntity<Object> handleCursoException(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse =  ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
