package edge.authorization.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class ProjectConfig extends WebSecurityConfigurerAdapter  {

	
//	@Override
//	protected UserDetailsService userDetailsService() {
	@Bean
	public UserDetailsService userDetailsService() {
		var manager = new InMemoryUserDetailsManager();
		
		UserDetails user1 = User.withUsername("jon")
								.password("pass")
								.authorities("read")
								.passwordEncoder(p -> encoder().encode(p))
								.build();
		UserDetails user2 = User.withUsername("john")
								.password(encoder().encode("12345"))
								.authorities("write")
								.build();

		manager.createUser(user1);
		manager.createUser(user2);
		return manager;
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService())
//			.passwordEncoder(encoder());
//	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
			.and()
			.formLogin()
				.defaultSuccessUrl("/home", true);
		http.authorizeRequests()
			.anyRequest()
			.authenticated();
	}
	
	
	
}
