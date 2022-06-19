package ApiTDD.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Getter
public class UserResponseDto {
	private Integer id;
	private String name;
	private String email;
}
