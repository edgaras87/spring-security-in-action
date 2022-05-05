package edge.project.service;

import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edge.project.data.UserRepository;
import edge.project.module.User;
import edge.project.security.CustomUserDetails;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public JpaUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// supplier to create exception instances
		Supplier<UsernameNotFoundException> s = () ->
			new UsernameNotFoundException("=== Problem during authentication! ===");
			
		User u = userRepository.findUserByUsername(username)
							   .orElseThrow(s);
		return new CustomUserDetails(u);
	}

}
