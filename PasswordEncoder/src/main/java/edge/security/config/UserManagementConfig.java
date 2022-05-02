package edge.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;


import edge.security.modules.SecureUser;

@Configuration
public class UserManagementConfig {

	@Bean
	List<UserDetails> usersInMemory() {
		UserDetails user = User.withUsername("jon").password(encoder().encode("pass")).authorities("read").build();

		User.UserBuilder builder1 = User.withUsername("bill");

		UserDetails user1 = builder1.password("pass").authorities("READ", "WRITE")
				.passwordEncoder(p -> encoder().encode(p)).accountExpired(false).disabled(true).build();

		UserDetails user2 = User.withUserDetails(user1).username("bill2").disabled(false).build();

		UserDetails user3 = new SecureUser(new edge.security.modules.User(null, "bob", encoder().encode("ppp"), "READ"));
		/**
		 * Users credentials 
		 * jon - pass bill - 
		 * pass (account disabled) 
		 * bill2 - pass
		 */
		
		return List.of(user, user1, user2, user3);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new CustomPasswordEncoder();
	}

}
