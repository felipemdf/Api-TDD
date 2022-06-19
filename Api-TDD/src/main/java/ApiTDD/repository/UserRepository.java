package ApiTDD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ApiTDD.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

}
