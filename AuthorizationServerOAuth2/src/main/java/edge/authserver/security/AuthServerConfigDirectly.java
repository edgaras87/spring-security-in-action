package edge.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @EnableAuthorizationServer - Instruct Spring Boot to enable the configuration 
 * specific to the OAuth 2 authorization server. We can customize this configuration 
 * by extending the AuthorizationServerConfigurerAdapter class and overriding 
 * specific methods.
 */
@Profile("directly")
@Configuration
@EnableAuthorizationServer
public class AuthServerConfigDirectly 
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
		
//		// 2.
//		clients.inMemory()
//				.withClient("client")
//				.secret("secret")
//				.authorizedGrantTypes("password", "refresh_token")
//				.scopes("read");
		
		// Using the password grant type (with refresh token grant type)
		clients.inMemory()
			   .withClient("client1")
			   .secret("secret1")
			   .authorizedGrantTypes("password", "refresh_token")
			   .scopes("read")
		// Using the authorization code grant type
			   .and()
			   .withClient("client2")
			   .secret("secret2")
			   .authorizedGrantTypes("authorization_code")
			   .scopes("read")
			   .redirectUris("http://localhost:9090/home")
		// Using the client credentials grant type
			   .and()
			   .withClient("client3")
			   .secret("secret3")
			   .authorizedGrantTypes("client_credentials")
			   .scopes("info")

			   
		// client registration for the resource server itself.
		// or in other words
		// Adding credentials for the resource server
		// don’t need any grant type or scope for the resource server to call the check_token endpoint
			   .and()
			   .withClient("resourceserver1")
			   .secret("resourceserversecret1");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// Specifies the condition for which we can call the check_token endpoint
		// Enabling authenticated access to the check_token endpoint
		security.checkTokenAccess("isAuthenticated()");
	}	
	
}
