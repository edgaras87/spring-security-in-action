package edge.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class SecureConfig extends WebSecurityConfigurerAdapter {

	// 1. using ClientRegistrationRepository as a bean 
	//@Bean public
	private ClientRegistrationRepository clienRepository() {
		var c = ClientRegistrationUsingClientRegistrationInterface();
		//var c = ClientRegistrationUsingCommonOAuth2Provider();
		return new InMemoryClientRegistrationRepository(c);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 2. using Customizer
		http.oauth2Login(
				c -> {
					c.clientRegistrationRepository(clienRepository());
				}
			);
		
		http.authorizeRequests()
			.anyRequest().authenticated();
		
	}
	
	// 1. by implementing ClientRegistration
	private ClientRegistration ClientRegistrationUsingClientRegistrationInterface() {
		return ClientRegistration.withRegistrationId("github")
				.clientId("e9bf209b3f9c0f05083d")
				.clientSecret("c733619993f1b67d54b6251a4e42ab6a75882396")
				 // of my choice
				.scope(new String [] {"read:user"})
				
				// The URI to which the client redirects the user for authentication
				.authorizationUri("https://github.com/login/oauth/authorize")
				
				// The URI that the client calls to obtain an access token and a refresh token
				.tokenUri("https://github.com/login/oauth/access_token")
				
				// The URI that the client can call after obtaining an access token 
				// to get more details about the user
				.userInfoUri("https://api.github.com/user")
				
				// of my choice
				.userNameAttributeName("id")
				
				// of my choice
				.clientName("GitHub") 
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUriTemplate("{baseUrl}/{action}/oauh2/code/{registrationId}")
				.build();
	}
	
	
	// 2. Using CommonOAuth2Provider
	private ClientRegistration ClientRegistrationUsingCommonOAuth2Provider() {
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId("e9bf209b3f9c0f05083d")
				.clientSecret("c733619993f1b67d54b6251a4e42ab6a75882396")
				.build();
	}
	
}
