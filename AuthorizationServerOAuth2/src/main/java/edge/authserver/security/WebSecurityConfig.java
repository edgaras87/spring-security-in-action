package edge.authserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig  
	extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		var manager = new InMemoryUserDetailsManager();
		var user = User.withUsername("jon")
						.password("pass")
						.authorities("read")
						.build();
		manager.createUser(user);
		return manager;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
	}

	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	// Adds the AuthenticationManager instance as a bean in the Spring context
	@Bean
    @Override
	public AuthenticationManager authenticationManagerBean() 
			throws Exception {
		return super.authenticationManagerBean();
	}
	
}
