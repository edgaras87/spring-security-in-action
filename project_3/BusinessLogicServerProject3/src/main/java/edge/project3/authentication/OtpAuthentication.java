package edge.project3.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * UsernamePasswordAuthenticationToken class implements Authentication 
 * interface and its defined methods. 
 * We can use the same class because we treat the OTP as a password
 */
public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	// when we call this, it sets the Authentication object as authenticated
	public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials);

	}

	// when we call this, the authentication instance remains unauthenticated,
	public OtpAuthentication(Object principal, Object credentials) {
		super(principal, credentials);

	}

}
