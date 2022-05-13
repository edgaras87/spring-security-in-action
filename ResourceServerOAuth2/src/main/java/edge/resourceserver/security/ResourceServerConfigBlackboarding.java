package edge.resourceserver.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Profile("BLACKBOARDING")
@Configuration
@EnableResourceServer
public class ResourceServerConfigBlackboarding 
	extends ResourceServerConfigurerAdapter {
	
	// Injects the data source we configured in the application.yml file
	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) 
				throws Exception {
		// Configures the token store
		resources.tokenStore(tokenStore());
	}
	
	@Bean
	public TokenStore tokenStore() {
		// Creates a JdbcTokenStore based on the injected data source
		return new JdbcTokenStore(dataSource);
	}
	
	
}
