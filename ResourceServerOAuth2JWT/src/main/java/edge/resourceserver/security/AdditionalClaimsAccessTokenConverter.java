package edge.resourceserver.security;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class AdditionalClaimsAccessTokenConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		// Applies the logic implemented by the JwtAccessTokenConverter class and gets the initial authentication object
		var authentication = super.extractAuthentication(map);
		// Adds the custom details to the authentication
		authentication.setDetails(map);
		
		return authentication;
	}
	
	
}
