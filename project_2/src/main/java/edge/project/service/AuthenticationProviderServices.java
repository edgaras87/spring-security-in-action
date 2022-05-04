package edge.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edge.project.security.CustomUserDetails;

@Service
public class AuthenticationProviderServices 
		implements AuthenticationProvider {

	private JpaUserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private SCryptPasswordEncoder sCryptPasswordEncoder;
	
	/**
	 * @Lazy - A simple way to break the cycle is by telling Spring to 
	 * initialize one of the beans lazily. So, instead of fully initializing
	 *  the bean, it will create a proxy to inject it into the other bean. 
	 *  The injected bean will only be fully created when it’s first needed.
	 *  
	 *  https://www.baeldung.com/circular-dependencies-in-spring
	 */
	@Autowired
	public AuthenticationProviderServices(
				  JpaUserDetailsService userDetailsService,
			@Lazy BCryptPasswordEncoder bCryptPasswordEncoder, 
			@Lazy SCryptPasswordEncoder sCryptPasswordEncoder) {

		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.sCryptPasswordEncoder = sCryptPasswordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) 
			throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		CustomUserDetails user = userDetailsService.loadUserByUsername(username);
		
		switch (user.getUser().getAlgorithm()) {
			case BCRYPT:
				return checkPassword(user, password, bCryptPasswordEncoder);
			case SCRYPT:
				return checkPassword(user, password, sCryptPasswordEncoder);
		}
		
		throw new BadCredentialsException("Bad credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}
	
	private Authentication checkPassword(CustomUserDetails user,
										 String rawPassword,
										 PasswordEncoder encoder) {
		if (encoder.matches(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(
									user.getUsername(),
									user.getPassword(),
									user.getAuthorities());
		} else {
			throw new BadCredentialsException("Bad credentials");
		}
	}

}
