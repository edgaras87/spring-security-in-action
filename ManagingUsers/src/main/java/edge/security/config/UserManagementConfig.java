package edge.security.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import edge.security.model.DummyUser;
import edge.security.model.SecurityUser;
import edge.security.model.SimpleUser;
import edge.security.service.InMemoryUserDetailService;

@Configuration
public class UserManagementConfig {

//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails user = User.withUsername("jon").password("pass").authorities("read").build();
//		
//		User.UserBuilder builder1 = User.withUsername("bill"); 
//		
//		UserDetails user1 = builder1.password("pass")
//									.authorities("RAED","WRITE")
//									.passwordEncoder(p -> encoder().encode(p))
//									.accountExpired(false)
//									.disabled(true)
//									.build();
//		
//		UserDetails user2 = User.withUserDetails(user1)
//								.username("bill2")
//								.disabled(false)
//								.build();
//		
//		UserDetails user3 = new DummyUser();
//		UserDetails user4 = new SimpleUser("simple", "simplepass");
//		UserDetails user5 = new SecurityUser(new edge.security.model.User(5, "bob", encoder().encode("ppp"), "READ"));
//		/**
//		 * Users credentials
//		 * jon    - pass
//		 * bill   - pass (account disabled)
//		 * bill2  - pass
//		 * dummy  - dum
//		 * simple - simplepass
//		 */
//		List<UserDetails> users = List.of(user, user1, user2, user3, user4, user5);
//		
//		return new InMemoryUserDetailService(users);
//	}

	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		String usersByUsernameQuery = "select username, password, enabled from user_management_security.users where username = ?";
		String authsByUserQuery = "select username, authority from user_management_security.authorities where username = ?";
		
		var userDetailsManager = new JdbcUserDetailsManager(dataSource);
		userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
		userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
		
		return userDetailsManager;
	}

}
