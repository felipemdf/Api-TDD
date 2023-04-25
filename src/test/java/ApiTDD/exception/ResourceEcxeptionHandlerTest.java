package ApiTDD.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import ApiTDD.controller.exception.DataIntegrityViolationException;
import ApiTDD.controller.exception.ObjectNotFoundException;
import ApiTDD.controller.exception.ResourceExceptionHandler;
import ApiTDD.controller.exception.StandartError;

@SpringBootTest
public class ResourceEcxeptionHandlerTest {
	
	@Autowired
    private ResourceExceptionHandler exceptionHandler;
	
	 @Test
	 void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
		 ResponseEntity<StandartError> response = exceptionHandler
				 .objectNotFound(
				 new ObjectNotFoundException("Object not found!"),
				 new MockHttpServletRequest()
	     );

	     assertNotNull(response);
	     assertNotNull(response.getBody());
	     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	     assertEquals(ResponseEntity.class, response.getClass());
	     assertEquals(StandartError.class, response.getBody().getClass());
	     assertEquals("Object not found!", response.getBody().getError());
	 }
	 
	 @Test
	 void whenDataIntegrityViolationExceptionThenReturnAResponseEntity() {
		 ResponseEntity<StandartError> response = exceptionHandler
				 .dataIntegrityViolationException(
				 new DataIntegrityViolationException("Email already exists!"),
				 new MockHttpServletRequest()
	     );

	     assertNotNull(response);
	     assertNotNull(response.getBody());
	     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	     assertEquals(ResponseEntity.class, response.getClass());
	     assertEquals(StandartError.class, response.getBody().getClass());
	     assertEquals("Email already exists!", response.getBody().getError());
	 }
}
