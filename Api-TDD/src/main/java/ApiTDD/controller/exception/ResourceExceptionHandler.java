package ApiTDD.controller.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException objectNotFoundException, HttpServletRequest  request){
		StandartError error = new StandartError(
				LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(), 
				objectNotFoundException.getMessage(), request.getRequestURI().toString()
		);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
		StandartError error = new StandartError(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(), 
				ex.getMessage(), request.getRequestURI().toString()
		);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
