package edge.security.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;


import edge.security.modules.SecureUser;

@Configuration
public class UserManagementConfig {

	@Bean
	List<UserDetails> usersInMemory() {
		
		String password = "{bcrypt}" + encoders().get("bcrypt").encode("pass");
		UserDetails user = User.withUsername("jon").password(password).authorities("read").build();

		User.UserBuilder builder1 = User.withUsername("bill");

		
		UserDetails user1 = builder1.password("pass")
									.authorities("READ", "WRITE")
									.passwordEncoder(p -> encoder().encode(p))
									// this will not work because of user storage in database!!!
									.accountExpired(true).disabled(true).build();

		UserDetails user2 = User.withUserDetails(user1)
								.username("bill2")
								.disabled(false)
								.build();

		password = "{my}"+ encoders().get("my").encode("ppp");
		UserDetails user3 = new SecureUser(new edge.security.modules.User(null, "bob", password, "READ"));
		
		password = "{myEncrypt}" + encoders().get("myEncrypt").encode("pass");
		UserDetails user4 = User.withUsername("tom").password(password).authorities("write").build();
		/**
		 * Users credentials 
		 * jon - pass 
		 * bill - pass (account disabled) 
		 * bill2 - pass
		 * bob - ppp
		 * tom - pass
		 */
		
		return List.of(user, user1, user2, user3, user4);
	}
	
	@Bean
	public Map<String, PasswordEncoder> encoders() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("my", new CustomPasswordEncoder());
		encoders.put("myEncrypt", new CustomKeyPasswordEncryptor());
		return encoders;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new DelegatingPasswordEncoder("pbkdf2", encoders());
	}

}
