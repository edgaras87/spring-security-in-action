package edge.project;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication//(exclude = SqlInitializationAutoConfiguration.class)
public class Project2Application {

	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}
	
	@Bean
	@Profile("orginal")
	public ApplicationRunner devProfile() {
		return args -> {
			System.out.println(" ===== ORGINAL ==== ");
		};
	}
	
	@Bean
	@Profile("update")
	public ApplicationRunner prodProfile() {
		return args -> {
			System.out.println(" ===== UPDATE ==== ");
		};
	}

}
