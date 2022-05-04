package edge.authentication.security.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableAsync // Enable separate threads
public class ProjectConfig extends WebSecurityConfigurerAdapter {
	
	private AuthenticationProvider authenticationProvider;
	private CustomAuthenticationFailureHandler authFailureHandler;
	private CustomAuthenticationSuccessHandler authSuccessHandler;

	public ProjectConfig(AuthenticationProvider authenticationProvider,
			CustomAuthenticationFailureHandler authFailureHandler,
			CustomAuthenticationSuccessHandler authSuccessHandler) {
		this.authenticationProvider = authenticationProvider;
		this.authFailureHandler = authFailureHandler;
		this.authSuccessHandler = authSuccessHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic(c -> {
				c.realmName("other");
				c.authenticationEntryPoint(new CustomEntryPoint());		
		});
		http.formLogin()
			//.defaultSuccessUrl("/page", true);
			.successHandler(authSuccessHandler)
			.failureHandler(authFailureHandler);
			
		http.authorizeHttpRequests().anyRequest().authenticated();
	}

	/**
	 * Works fine without this configuration !!!
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		auth.authenticationProvider(authenticationProvider);
	}
	
	/**
	 * MODE_THREADLOCAL - security context does not shared by any threads of the application
	 * MODE_INHERITABLETHREADLOCAL - the framework copies the security context details 
	 * 		from the original thread of the request to the security context of the new thread derived
	 * 		from orginal thread.
	 * MODE_GLOBAL - security context shared by all the threads of the application.
	 * 		(doesn’t fit the general picture of the web server application, 
	 * 		but can be a good use for a standalone application)  
	 */  
	public InitializingBean initializingBean() {
		return () -> {
			SecurityContextHolder.setStrategyName(
					//SecurityContextHolder.MODE_THREADLOCAL
					SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
					//SecurityContextHolder.MODE_GLOBAL
					);
		};
	}
	
}
