package edge.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edge.security.data.UserRepository;
import edge.security.modules.SecureUser;
import edge.security.modules.User;

@Service
public class UserRepoUserDetailsService implements UserDetailsService {

	private UserRepository userRepo;
	
	@Autowired
	public UserRepoUserDetailsService(UserRepository userRepo, List<UserDetails> inMemoryusers) {
		this.userRepo = userRepo;
		for(UserDetails user : inMemoryusers)
			this.userRepo.save(
					new User(null, 
							user.getUsername(), 
							user.getPassword(), 
							user.getAuthorities().iterator().next().getAuthority()
							));
	}
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new SecureUser(userRepo.findByUsername(username).get());
	}

	
	
	
	
}
