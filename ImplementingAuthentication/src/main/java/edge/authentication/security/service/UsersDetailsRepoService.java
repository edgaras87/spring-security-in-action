package edge.authentication.security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edge.authentication.data.UserRepository;
import edge.authentication.modules.User;

@Service
public class UsersDetailsRepoService implements UserDetailsService {

	private final List<UserDetails> users;
	private final UserRepository userRepo;

	public UsersDetailsRepoService(List<UserDetails> users, UserRepository userRepo) {
		this.users = users;
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUsername(username);
		if (user != null)
			return user;
		else
			return users.stream()
						.filter(u -> u.getUsername().equals(username))
						.findFirst()
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
