package ApiTDD.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public UserModel findById(Integer id) {
		Optional<UserModel> user = userRepository.findById(Long.valueOf(id));
		
		return user.orElseThrow(() -> new ObjectNotFoundException(1 ,"User not found"));
	}

	public List<UserModel> findAll() {
		List<UserModel> user = userRepository.findAll();
		return user;
	}

	public UserResponseDto create(UserModel user) {
		return null;
	}

}
