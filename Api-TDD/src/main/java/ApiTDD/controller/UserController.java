package ApiTDD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	public ResponseEntity<UserResponseDto> findById(int i) {
		return null;
	}

	public ResponseEntity<List<UserResponseDto>> findAll() {
		return null;
	}

	public ResponseEntity<UserResponseDto> create(UserModel user) {
		return null;
	}

	public ResponseEntity<UserResponseDto> delete(UserModel user) {
		return null;
	}
	
	
}
