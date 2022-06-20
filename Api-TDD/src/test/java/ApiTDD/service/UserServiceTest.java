package ApiTDD.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.repository.UserRepository;
import localhost.gestor_eventos_back_end.exception.ObjectNotFoundException;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	private UserModel user;
	private Optional<UserModel> optionalUser;
	private UserResponseDto userResponseDto;
	
	
	
	@BeforeEach
	void setup () {
		user = new UserModel(1, "joao", "joao@gmail.com", "1234", LocalDateTime.now(), LocalDateTime.now());
		userResponseDto = new UserResponseDto(1, "joao", "joao@gmail.com");
		optionalUser = Optional.of(new UserModel(1, "joao", "joao@gmail.com", "1234", LocalDateTime.now(), LocalDateTime.now()));
	}
	
	@Test
	void whenFindByIdThenReturnAnUser() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		UserResponseDto response = userService.findById(1);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponseDto.class, response.getClass(), "Should return a User Dto");
		Assertions.assertEquals(optionalUser.get().getId(), response.getId());
		Assertions.assertEquals(optionalUser.get().getName(), response.getName());
		Assertions.assertEquals(optionalUser.get().getEmail(), response.getEmail());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("User not found!"));
		
		try {
			userService.findById(1);
			fail("Expected exception was not thrown");
		} catch (Exception e) {
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass(), "Should throw a exception ObjectNotFoundException");
			Assertions.assertEquals("User not found!", e.getMessage(), "Should throw a exception message \"User not found!\"");
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user));
		
		List<UserResponseDto> response = userService.findAll();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(1, response.size(), "Should return a list of one User");
		Assertions.assertEquals(UserResponseDto.class, response.get(0).getClass(), "Should return a list of UserModel Dto");
		Assertions.assertEquals(user.getId(), response.get(0).getId());
		Assertions.assertEquals(user.getName(), response.get(0).getName());
		Assertions.assertEquals(user.getEmail(), response.get(0).getEmail());

	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		UserResponseDto response = userService.create(user);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponseDto.class, response.getClass(), "Should return a User Dto");
		Assertions.assertEquals(optionalUser.get().getId(), response.getId());
		Assertions.assertEquals(optionalUser.get().getName(), response.getName());
		Assertions.assertEquals(optionalUser.get().getEmail(), response.getEmail());
	}
	
	@Test
	void whenCreateThenReturnAndDataIntegrityViolationException() {
		optionalUser.get().setId(2);
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		try {
			userService.create(user);
			fail("Expected exception was not thrown");
		} catch (Exception e) {
			Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass(), "Should throw a exception DataIntegrityViolationException");
			Assertions.assertEquals("Email already exists!", e.getMessage(), "Should throw a exception message \"Email already exists!\"");
		}
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		UserResponseDto response = userService.update(user);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponseDto.class, response.getClass(), "Should return a User Dto");
		Assertions.assertEquals(user.getId(), response.getId());
		Assertions.assertEquals(user.getName(), response.getName());
		Assertions.assertEquals(user.getEmail(), response.getEmail());
	}
	
	@Test
	void whenUpdateThenReturnAndDataIntegrityViolationException() {
		optionalUser.get().setId(2);
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		try {
			userService.update(user);
			fail("Expected exception was not thrown");
		} catch (Exception e) {
			Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass(), "Should throw a exception DataIntegrityViolationException");
			Assertions.assertEquals("Email already exists!", e.getMessage(), "Should throw a exception message \"Email already exists!\"");
		}
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(userRepository).deleteById(anyLong());
		
		userService.delete(1);
		verify(userRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void whenDeleteThenReturnAnObjectNotFoundException() {
		doThrow(new ObjectNotFoundException("User not found!")).when(userRepository).deleteById(anyLong());
		
		try {
			userService.delete(1);
		} catch (Exception e) {
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass(), "Should throw a exception ObjectNotFoundException");
			Assertions.assertEquals("User not found!", e.getMessage(), "Should throw a exception message \"User not found!\"");
		}
	}
}
