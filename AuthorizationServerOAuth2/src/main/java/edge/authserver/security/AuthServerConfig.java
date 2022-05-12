package edge.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
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
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {

//		// 1.
//		// Creates an instance using the ClientDetailsService implementation
//		var service = new InMemoryClientDetailsService();
//		
//		// Creates an instance of ClientDetails and sets the needed details about the client
//		var client = new BaseClientDetails();
//		client.setClientId("client");
//		client.setClientSecret("secret");
//		client.setScope(List.of("read"));
//		client.setAuthorizedGrantTypes(List.of("password"));
//		
//		// Adds the ClientDetails instance to InMemoryClientDetailsService
//		service.setClientDetailsStore(Map.of("client", client));
//		
//		// Configures ClientDetailsService for use by our authorization server
//		clients.withClientDetails(service);
		
		// 2.
		clients.inMemory()
				.withClient("client")
				.secret("secret")
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("read");
		
		
	}	
	
}
