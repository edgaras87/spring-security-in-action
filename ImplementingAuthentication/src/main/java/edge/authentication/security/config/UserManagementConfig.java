package edge.authentication.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserManagementConfig {

	@Bean
	List<UserDetails> usersInMemory() {

		User.UserBuilder builder = User.builder().passwordEncoder(p -> encoder().encode(p)).authorities("read");

		UserDetails user1 = builder.username("jon").password("pass").build();
		UserDetails user2 = builder.username("bob").password("ass").build();

		edge.authentication.modules.User user3 = new edge.authentication.modules.User(null, "tom",
				encoder().encode("pass"), "write");

		return Arrays.asList(user1, user2, user3);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
