package edge.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import edge.project.service.AuthenticationProviderServices;

@Profile("orginal")
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationProviderServices authProviderServices;
	
	/**
	 * @Lazy - A simple way to break the cycle is by telling Spring to 
	 * initialize one of the beans lazily. So, instead of fully initializing
	 *  the bean, it will create a proxy to inject it into the other bean. 
	 *  The injected bean will only be fully created when it’s first needed.
	 *  
	 *  https://www.baeldung.com/circular-dependencies-in-spring
	 */
	@Autowired
	public ProjectConfig(@Lazy  AuthenticationProviderServices authProviderServices) {
		this.authProviderServices = authProviderServices;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SCryptPasswordEncoder sCryptPasswordEncoder() {
		return new SCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProviderServices);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.defaultSuccessUrl("/main", true);
		http.authorizeHttpRequests()
				.anyRequest().authenticated();
	}
	
	

}
