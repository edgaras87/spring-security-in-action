package edge.project3.authentication;


import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * UsernamePasswordAuthenticationToken class implements Authentication 
 * interface and its defined methods. 
 */
public class UsernamePasswordAuthentication
	extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	// when we call this, it sets the Authentication object as authenticated
	public UsernamePasswordAuthentication(
			Object principal, 
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	
	}
	// when we call this, the authentication instance remains unauthenticated,
	public UsernamePasswordAuthentication(
			Object principal, 
			Object credentials) {
		super(principal, credentials);
	
	}

}
