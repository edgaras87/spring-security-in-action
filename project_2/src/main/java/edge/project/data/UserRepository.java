package edge.project.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edge.project.module.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByUsername(String username);
}
