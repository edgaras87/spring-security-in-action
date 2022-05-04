package edge.authentication.security.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;
	private PasswordEncoder encoder;
	
	public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
		this.userDetailsService = userDetailsService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (encoder.matches(password, user.getPassword())) 
			return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
		else
			throw new BadCredentialsException("Something went wrong!");
	}

	// Class<?> authentication - defines what authentications Authentication provider provides to user 
	@Override
	public boolean supports(Class<?> authentication) {
		// provides a standard authentication request with username and password
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
