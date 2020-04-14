package com.Tarea01.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*Sin ésta anotación, una petición GET sobre /pacientes/55 despliega error 500 
 *Internal Server Error
 *Con esta anotación, se obtiene error: 404 Not Found 
 *Que es lo que se busca con el StatusCode HttpStatus.NOT_FOUND*/

//@ResponseStatus(HttpStatus.NOT_FOUND) 
public class ModeloNotFoundEXception extends RuntimeException{
	
	public ModeloNotFoundEXception(String mensaje) {
		super(mensaje);
	}
}
