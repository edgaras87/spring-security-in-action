package edge.authserver.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Profile("blackboarding")
@Configuration
@EnableAuthorizationServer
public class AuthServerConfigBlackboarding 
	extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	// Injects the data source configured in the application.yml file
	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		clients
				.inMemory()
					// Using the password grant type (with refresh token grant type)
			   		.withClient("client1")
			   		.secret("secret1")
			   		.authorizedGrantTypes("password", "refresh_token")
			   		.scopes("read");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager)
				 // Configures the token store
				 .tokenStore(tokenStore());
	}

	@Bean
	public TokenStore tokenStore() {
		// Creates an instance of JdbcTokenStore, providing access to the database through the data source
		return new JdbcTokenStore(dataSource);
	}
}
