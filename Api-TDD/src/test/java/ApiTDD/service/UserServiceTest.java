package ApiTDD.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
		Assertions.assertEquals(UserModel.class, response.getClass(), "Should return a User class");
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
			
		} catch (Exception e) {
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass(), "Should throw a exception ObjectNotFoundException");
			Assertions.assertEquals("User not found!", e.getMessage(), "Should throw a exception message \"User not found!\"");
		}
	}
}
