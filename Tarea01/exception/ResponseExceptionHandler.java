package com.mitocode.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*Ante cualquier tipo de error, esta clase la va a interceptar*/
@ControllerAdvice /*Interceptador de exceptiones que colocaremos*/
@RestController /*Para exponer la respuesta como servicio REST*/
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ModeloNotFoundEXception.class)
	public final ResponseEntity<ExceptionResponse> manejarModeloException(ModeloNotFoundEXception ex, WebRequest request){
		
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));		
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
		/*Ahora esto maneja la excepción de manera personalizada*/
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		return super.handleMethodArgumentNotValid(ex, headers, status, request);
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));		
		return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
	}
	
	
}
