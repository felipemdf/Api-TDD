package ApiTDD.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiTDD.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByEmail(String anyString);

}
