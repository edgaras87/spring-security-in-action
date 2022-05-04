package edge.authentication;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import edge.authentication.data.UserRepository;
import edge.authentication.modules.User;


@SpringBootApplication
public class ImplementingAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImplementingAuthenticationApplication.class, args);
	}
	
	@Bean
	ApplicationRunner populateUserDatabase(UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {
			User user1 = new User(null, "toby", encoder.encode("ttt"), "write");
			User user2 = new User(null, "foby", encoder.encode("fff"), "delete");
			User user3 = new User(null, "goby", encoder.encode("ggg"), "update");
			userRepo.saveAll(Arrays.asList(user1, user2, user3));
		};
	}

}
