package edge.authorization.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
								.roles("ADMIN")
								.authorities("premium")
								.passwordEncoder(p -> encoder().encode(p))
								.build();
		UserDetails user2 = User.withUsername("john")
								.password(encoder().encode("12345"))
								.roles("MANAGER")
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
			.formLogin();
		http.authorizeRequests()
			// 1.1 mvcMatchers(String) 
			.mvcMatchers("/home").hasRole("ADMIN")
			.mvcMatchers("/ciao").hasRole("MANAGER")
			.mvcMatchers("/hola").authenticated()
			// 1.2 mvcMatchers(HTTPmethod, String) 
			.mvcMatchers(HttpMethod.GET, "/a").authenticated()
			.mvcMatchers(HttpMethod.POST, "/a").permitAll()
			
			// multiple paths
			.mvcMatchers("/a/b/**").authenticated()
			
			// path variable with regex
			.mvcMatchers("/product/{code:^[0-9]*$}").permitAll()
			
			
			// 2. antMatchers(String), mvcMatchers(HTTPmethod, String) mvcMatchers(HTTPmethod)
			.antMatchers("/d").authenticated()
			

			// 3. mvcMatchers(String), mvcMatchers(HTTPmethod, String)
			.regexMatchers(".*/(us|uk|ca)+/(en|fr).*").permitAll()
			.mvcMatchers("/video/**").hasAuthority("premium")
			
			// authenticated user can see the video content if the request 
			// comes from the US, Canada, or the UK, or if they use English. 
			
			// this is by default
			//.anyRequest().permitAll()
			
			.anyRequest().denyAll()
			;
		// turns off cross-site request forgery protection
		// for POST DELETE PUT HTTPmethods
		http.csrf().disable();
	}
	
	
	
}
