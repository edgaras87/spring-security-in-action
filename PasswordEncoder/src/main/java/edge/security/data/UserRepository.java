package edge.security.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edge.security.modules.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
}
