package security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import security.pojo.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	
	//Optional<User> findByEmailId(String emailId);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
