package ApiTDD.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.service.UserService;

@SpringBootTest
public class UserControllerTest {
	
	
	@Autowired
	UserController userController;
	
	@MockBean
	UserService userService;
	
	private UserModel user;
	private UserResponseDto userResponseDto;
	
	@BeforeEach
	void setup () {
		user = new UserModel(1, "joao", "joao@gmail.com", "1234", LocalDateTime.now(), LocalDateTime.now());
		userResponseDto = new UserResponseDto(1, "joao", "joao@gmail.com");
	}
	
	@Test
	void whenFindByIdThenReturnAnSuccess() {
		when(userService.findById(anyInt())).thenReturn(user);
		ResponseEntity<UserResponseDto> response = userController.findById(1);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return a status code 200");
		Assertions.assertEquals(ResponseEntity.class, response.getClass(), "Should return a ResponseEntity");
		Assertions.assertEquals(UserResponseDto.class, response.getBody().getClass(), "Should return a UserReponseDto");
		Assertions.assertEquals(user.getId(), response.getBody().getId());
		Assertions.assertEquals(user.getEmail(), response.getBody().getEmail());
		Assertions.assertEquals(user.getName(), response.getBody().getName());
	}
	
	
	@Test
	void whenFindAllThenReturnAListOfUserReponseDto() {
		when(userService.findAll()).thenReturn(List.of(user));
		ResponseEntity<List<UserResponseDto>> response = userController.findAll();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return a status code 200");
		Assertions.assertEquals(1, response.getBody().size(), "Should return size 1");
		Assertions.assertEquals(ResponseEntity.class, response.getClass(), "Should return a ResponseEntity");
		Assertions.assertEquals(ArrayList.class, response.getBody().getClass(), "Should return a ArrayList");
		Assertions.assertEquals(UserResponseDto.class, response.getBody().get(0).getClass(), "Should return a UserReponseDto");
		Assertions.assertEquals(user.getId(), response.getBody().get(0).getId());
		Assertions.assertEquals(user.getEmail(), response.getBody().get(0).getEmail());
		Assertions.assertEquals(user.getName(), response.getBody().get(0).getName());
	}
	
	@Test
	void whenCreateThenReturnCreated() {
		when(userService.create(any(UserModel.class))).thenReturn(user);
		ResponseEntity<UserResponseDto> response = userController.create(user);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Should return a status code 201");
		Assertions.assertEquals(ResponseEntity.class, response.getClass(), "Should return a ResponseEntity");
		Assertions.assertNotNull(response.getHeaders().get("Location"));
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(userService.update(any(UserModel.class))).thenReturn(user);
		ResponseEntity<UserResponseDto> response = userController.update(1, user);
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return a status code 200");
		Assertions.assertEquals(ResponseEntity.class, response.getClass(), "Should return a ResponseEntity");
		Assertions.assertEquals(UserResponseDto.class, response.getBody().getClass(), "Should return a UserReponseDto");
		Assertions.assertEquals(user.getId(), response.getBody().getId());
		Assertions.assertEquals(user.getEmail(), response.getBody().getEmail());
		Assertions.assertEquals(user.getName(), response.getBody().getName());
	}
	
	
	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(userService).delete(anyInt());
		ResponseEntity<UserResponseDto> response = userController.delete(1);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(ResponseEntity.class, response.getClass(), "Should return a ResponseEntity");
		Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Should return a status code 204");
		verify(userService, times(1)).delete(anyInt());
	}
}
