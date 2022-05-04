package edge.authentication.data;

import org.springframework.data.repository.CrudRepository;

import edge.authentication.modules.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String user);
}
