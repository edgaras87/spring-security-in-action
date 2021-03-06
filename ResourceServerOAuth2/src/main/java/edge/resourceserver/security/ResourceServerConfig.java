package edge.resourceserver.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Profile("CLOUD")
@Configuration
@EnableResourceServer
public class ResourceServerConfig 
	extends  WebSecurityConfigurerAdapter {
	
}
