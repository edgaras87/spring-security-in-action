package edge.authorization.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import edge.authorization.security.filter.AuthenticationLoggingFilter;
import edge.authorization.security.filter.RequestValidationFilter;
import edge.authorization.security.filter.StaticKeyAuthenticationFilter;

@Service
public class ProjectConfig extends WebSecurityConfigurerAdapter  {


	private StaticKeyAuthenticationFilter filter;
	
	public ProjectConfig(StaticKeyAuthenticationFilter filter) {
		this.filter = filter;
	}

	
//	@Override
//	protected UserDetailsService userDetailsService() {
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		var manager = new InMemoryUserDetailsManager();
		
		UserDetails user1 = User.withUsername("jon")
								.password("pass")
								.roles("ADMIN")
								.passwordEncoder(p -> encoder.encode(p))
								.build();
		UserDetails user2 = User.withUsername("john")
								.password(encoder.encode("12345"))
								.roles("MANAGER")
								.build();
		UserDetails user3 = User.withUsername("ben")
								.password("bbb")
								.authorities("premium")
								.passwordEncoder(p -> encoder.encode(p))
								.build();
		manager.createUser(user1);
		manager.createUser(user2);
		manager.createUser(user3);
				
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
		http
		.httpBasic()
		.and()
		.formLogin();
		
		http
			// Adding a filter before an existing one in the chain
			.addFilterBefore(
				new RequestValidationFilter(),
				BasicAuthenticationFilter.class)
			// Adding a filter after an existing one in the chain
			.addFilterAfter(
					new AuthenticationLoggingFilter(),
					BasicAuthenticationFilter.class)
			// Adding a filter at an existing one in the chain
			.addFilterAt(filter, BasicAuthenticationFilter.class)
				.authorizeRequests()
					.anyRequest().permitAll()
			;
		
		// all these matchers are commented because of addFilterAt() 
//		http.authorizeRequests()
//			// 1.1 mvcMatchers(String) 
//			.mvcMatchers("/home").hasRole("ADMIN")
//			.mvcMatchers("/ciao").hasRole("MANAGER")
//			.mvcMatchers("/hola").authenticated()
//			// 1.2 mvcMatchers(HTTPmethod, String) 
//			.mvcMatchers(HttpMethod.GET, "/a").authenticated()
//			.mvcMatchers(HttpMethod.POST, "/a").permitAll()
//			
//			// multiple paths
//			.mvcMatchers("/a/b/**").authenticated()
//			
//			// path variable with regex
//			.mvcMatchers("/product/{code:^[0-9]*$}").permitAll()
//			
//			
//			// 2. antMatchers(String), mvcMatchers(HTTPmethod, String) mvcMatchers(HTTPmethod)
//			.antMatchers("/d").authenticated()
//			
//			// 3. mvcMatchers(String), mvcMatchers(HTTPmethod, String)
//			// authenticated user can see the video content if the request 
//			// comes from the US, Canada, or the UK, or if they use English. 
//			.regexMatchers(".*/(us|uk|ca)+/(en|fr).*").permitAll()
//			.mvcMatchers("/video/**").hasAuthority("premium")
//			
//			
//			// this is by default
//			//.anyRequest().permitAll()
//			.anyRequest().authenticated()
//			;
//		// turns off cross-site request forgery protection
//		// for POST DELETE PUT HTTPmethods
		
		http.csrf().disable();
	}
	
	
	
}
