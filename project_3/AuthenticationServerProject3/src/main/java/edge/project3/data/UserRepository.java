package edge.project3.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edge.project3.module.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findUserByUsername(String username);
}
