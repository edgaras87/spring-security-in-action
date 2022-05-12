package edge.resourceserver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("INTROSPECTION")
@Configuration
public class ResourceServerConfigIntrospection	
	extends  WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
			.and()
				.oauth2ResourceServer(
						c -> c.opaqueToken(
								o -> {
									// endpoint of the authorization server for token check
			                        o.introspectionUri("http://localhost:8080/oauth/check_token");
									// credentials of this resource server for authentication server  
			                        o.introspectionClientCredentials("resourceserver1", "resourceserversecret1");
								})
						);
	}
}
