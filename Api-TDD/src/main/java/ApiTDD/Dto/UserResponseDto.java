package ApiTDD.Dto;

import java.util.ArrayList;
import java.util.List;

import ApiTDD.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Getter
public class UserResponseDto {
	private Integer id;
	private String name;
	private String email;
	
	
	
	public static UserResponseDto toUserReponseDto(UserModel user) {
		return new UserResponseDto(
			user.getId(),
			user.getName(),
			user.getEmail()
		);
	}
	
	public static List<UserResponseDto> listToUserReponseDto(List<UserModel> listUser) {
		List<UserResponseDto> listUserDto = new ArrayList<>();
		
		listUser.forEach(user -> listUserDto.add(
				new UserResponseDto(
						user.getId(),
						user.getName(),
						user.getEmail()
				)
		));
		
		return listUserDto;
	}
}
