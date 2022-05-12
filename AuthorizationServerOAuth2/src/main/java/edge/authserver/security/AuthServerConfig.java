package edge.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;


/**
 * @EnableAuthorizationServer - Instruct Spring Boot to enable the configuration 
 * specific to the OAuth 2 authorization server. We can customize this configuration 
 * by extending the AuthorizationServerConfigurerAdapter class and overriding 
 * specific methods.
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig 
		extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager);
	}
	
}
