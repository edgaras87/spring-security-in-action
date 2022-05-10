package edge.csrf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import edge.csrf.security.filter.CsrfTokenLogger;

@Profile("CSRF")
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
					.withUser("jon")
					.password(encoder().encode("pass"))
					.authorities("read");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
			.and()
			.formLogin()
				.defaultSuccessUrl("/main", true);
		http.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
			.authorizeRequests()
				.anyRequest()
					//.authenticated();
					.permitAll();
		http.csrf(c -> {
			
			c.csrfTokenRepository(customTokenRepository());
			
			// 1. antMatcher
			c.ignoringAntMatchers("/ciao");
			
			// 2. mvcMatcher
			HandlerMappingIntrospector i = new HandlerMappingIntrospector();
			MvcRequestMatcher m = new MvcRequestMatcher(i, "/hola");
			c.ignoringRequestMatchers(m);
			
			// 3. regexMatcher
			String pattern = ".*[0-9].*";
			String httpMethod = HttpMethod.POST.toString();
			RegexRequestMatcher r = new RegexRequestMatcher(pattern, httpMethod);
		});
	}

	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public CsrfTokenRepository customTokenRepository() {
		return new CustomCsrfTokenRepository();
	}
	
}
