package es.cic.bootcamp.individual06final.controller;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import es.cic.bootcamp.individual06final.exception.CursoException;
import es.cic.bootcamp.individual06final.exception.CursoProgramadoException;
import es.cic.bootcamp.individual06final.exception.TematicaException;
import io.micrometer.core.lang.Nullable;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler 
   {

    
    @ExceptionHandler(value 
      = {MethodArgumentNotValidException.class})
      public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {
        String bodyOfResponse = "No se ha podido realizar la operación. Los datos introducidos en el formulario no son correctos.";
        return handleExceptionInternal(bodyOfResponse, 
          new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value 
    = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String bodyOfResponse = "No se ha podido realizar la operación. Petición no implementada";
        return handleExceptionInternal(bodyOfResponse, 
          new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value 
      = {TematicaException.class})
      public ResponseEntity<Object> handleTematicaException(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse =  ex.getMessage();
        return handleExceptionInternal(bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value 
      = {CursoException.class})
    public ResponseEntity<Object> handleCursoException(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse =  ex.getMessage();
        return handleExceptionInternal(bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value 
    = {CursoProgramadoException.class})
  public ResponseEntity<Object> handleCursoProgramadoException(
    RuntimeException ex, WebRequest request) {
      String bodyOfResponse =  ex.getMessage();
      return handleExceptionInternal(bodyOfResponse, 
        new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

    private ResponseEntity<Object> handleExceptionInternal( @Nullable Object bodyOfResponse,
        HttpHeaders httpHeaders, HttpStatus status) {

      return  new ResponseEntity<>(bodyOfResponse, httpHeaders, status);
    }

    
}
