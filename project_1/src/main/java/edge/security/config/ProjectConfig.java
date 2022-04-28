package edge.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic(); // by default, app uses HTTP Basic authentication
		http.authorizeRequests()
				.anyRequest()        // all requests(endpoints)
					.authenticated() // require authentication
					//.permitAll()   // accessible without credentials 
					
					
		;
	}

	@Bean
	public UserDetailsService userDetailsService() {

		InMemoryUserDetailsManager userDetailsService =  new InMemoryUserDetailsManager();
		UserDetails user = User.withUsername("jon")
				        .password("pass")
				        .authorities("read")
				        .build();
		
		userDetailsService.createUser(user);
		return userDetailsService;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	

	
}
