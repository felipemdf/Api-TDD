package ApiTDD.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
	
	static private UserModel user;
	static private Optional<UserModel> optionalUser;
	static private UserResponseDto userResponseDto;
	
	
	
	@BeforeAll
	static void setup () {
		user = new UserModel(1, "joao", "joao@gmail.com", "1234", LocalDateTime.now(), LocalDateTime.now());
		userResponseDto = new UserResponseDto(1, "joao", "joao@gmail.com");
		optionalUser = Optional.of(user);
	}
	
	@Test
	void whenFindByIdThenReturnAnUser() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		UserModel response = userService.findById(1);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserModel.class, response.getClass(), "Should return a User");
		Assertions.assertEquals(user.getId(), response.getId());
		Assertions.assertEquals(user.getName(), response.getName());
		Assertions.assertEquals(user.getEmail(), response.getEmail());
		Assertions.assertEquals(user.getPassword(), response.getPassword());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(userRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("User not found!"));
		
		try {
			UserModel response = userService.findById(1);
			fail("Expected exception was not thrown");
		} catch (Exception e) {
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass(), "Should throw a exception ObjectNotFoundException");
			Assertions.assertEquals("User not found!", e.getMessage(), "Should throw a exception message \"User not found!\"");
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user));
		
		List<UserModel> response = userService.findAll();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(1, response.size(), "Should return a list of one User");
		Assertions.assertEquals(UserModel.class, response.get(0).getClass(), "Should return a list of UserModel");
		Assertions.assertEquals(user.getId(), response.get(0).getId());
		Assertions.assertEquals(user.getName(), response.get(0).getName());
		Assertions.assertEquals(user.getEmail(), response.get(0).getEmail());
		Assertions.assertEquals(user.getPassword(), response.get(0).getPassword());

	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		UserResponseDto response = userService.create(user);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponseDto.class, response.getClass(), "Should return a UserModel");
		Assertions.assertEquals(user.getId(), response.getId());
		Assertions.assertEquals(user.getName(), response.getName());
		Assertions.assertEquals(user.getEmail(), response.getEmail());
	}
	
	@Test
	void whenCreateThenReturnAndDataIntegrityViolationException() {
		when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
		when(userRepository.save(any(UserModel.class))).thenReturn(user);
		
		try {
			UserResponseDto response  = userService.create(user);
			fail("Expected exception was not thrown");
		} catch (Exception e) {
			Assertions.assertEquals(DataIntegrityViolationException.class, e.getClass(), "Should throw a exception DataIntegrityViolationException");
			Assertions.assertEquals("Email already exists!", e.getMessage(), "Should throw a exception message \"Email already exists!\"");
		}
		

	}
}
