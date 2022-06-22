package ApiTDD.controller.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter 
public class StandartError {
	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String path;
	
}
