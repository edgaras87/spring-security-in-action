package edge.security;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasswordEncoderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordEncoderApplication.class, args);
	}
	
	@Bean
	ApplicationRunner cryptographyExamples() {
		return args -> {
			CryptographyExamples.generateKeyExamples();
			CryptographyExamples.encryptorsExamples();
		};
	}
	

}
