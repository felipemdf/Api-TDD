package ApiTDD.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<UserResponseDto> findById(@PathVariable Integer id) {
		UserModel user = userService.findById(id);
		
		UserResponseDto userDto = UserResponseDto.toUserReponseDto(user);
		return ResponseEntity.ok().body(userDto);
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> findAll() {
		List<UserModel> users = userService.findAll();
		
		List<UserResponseDto> usersDto = UserResponseDto.listToUserReponseDto(users);
		return ResponseEntity.ok().body(usersDto);
	}

	@PostMapping
	public ResponseEntity<UserResponseDto> create(UserModel user) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("1")
				.buildAndExpand(userService.create(user))
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PatchMapping
	public ResponseEntity<UserResponseDto> update (@PathVariable Integer id, UserModel user) {
		user.setId(id);
		UserModel newUser = userService.update(user);
		UserResponseDto userDto = UserResponseDto.toUserReponseDto(newUser);
		
		return ResponseEntity.ok().body(userDto);
	}

	@DeleteMapping
	public ResponseEntity<UserResponseDto> delete(@PathVariable Integer id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
