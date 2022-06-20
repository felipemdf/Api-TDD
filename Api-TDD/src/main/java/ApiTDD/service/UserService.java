package ApiTDD.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ApiTDD.Dto.UserResponseDto;
import ApiTDD.model.UserModel;
import ApiTDD.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public UserResponseDto findById(Integer id) {
		Optional<UserModel> user = userRepository.findById(Long.valueOf(id));
		UserResponseDto userDto = UserResponseDto.toUserReponseDto(user.orElseThrow(() -> new ObjectNotFoundException(1 ,"User not found")));
		
		return userDto;
	}

	public List<UserResponseDto> findAll() {
		List<UserModel> ListUser = userRepository.findAll();
		List<UserResponseDto> listUserDto = UserResponseDto.listToUserReponseDto(ListUser);
		
		return listUserDto;
	}

	public UserResponseDto create(UserModel newUser) {
		validEmail(newUser);
		UserModel user = userRepository.save(newUser);
		
		return UserResponseDto.toUserReponseDto(user);
	}
	
	public UserResponseDto update(UserModel newUser) {
		validEmail(newUser);
		UserModel user = userRepository.save(newUser);
		return UserResponseDto.toUserReponseDto(user);
	}

	public void delete(Integer id) {
		userRepository.deleteById(Long.valueOf(id));
	}
	
	public void validEmail(UserModel newUser) {
		Optional<UserModel> user = userRepository.findByEmail(newUser.getEmail());
		
		if(user.isPresent() && !user.get().getId().equals(newUser.getId()))
			throw new DataIntegrityViolationException("Email already exists!");
	}

}
