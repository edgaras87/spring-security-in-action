package edge.csrf.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Profile("CORS")
@Configuration
public class ConfigCORS extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Applying CORS using a CorsConfigurer
		http.cors(c -> {
			
			CorsConfigurationSource source = request -> {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(List.of("example.com","example.org"));
				config.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
				return config;
			};
			c.configurationSource(source);
		});
		
		http.csrf()
				.disable();
		http.authorizeRequests()
				.anyRequest().permitAll();
		
	}
	
	
	
}
