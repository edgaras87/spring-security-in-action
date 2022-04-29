package edge.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import edge.security.model.DummyUser;
import edge.security.model.SimpleUser;

@Configuration
public class UserManagementConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {

		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		
		UserDetails user = User.withUsername("jon").password("pass").authorities("read").build();
		
		User.UserBuilder builder1 = User.withUsername("bill"); 
		
		UserDetails user1 = builder1.password("pass")
									.authorities("RAED","WRITE")
									.passwordEncoder(p -> encoder().encode(p))
									.accountExpired(false)
									.disabled(true)
									.build();
		
		UserDetails user2 = User.withUserDetails(user1)
								.username("bill2")
								.disabled(false)
								.build();

		/**
		 * Users credentials
		 * jon    - pass
		 * bill   - pass (account disabled)
		 * bill2  - pass
		 * dummy  - dum
		 * simple - simplepass
		 */
		userDetailsService.createUser(user);
		userDetailsService.createUser(user1);
		userDetailsService.createUser(user2);
		userDetailsService.createUser(new DummyUser());
		userDetailsService.createUser(new SimpleUser("simple", "simplepass"));
		
		
		return userDetailsService;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
