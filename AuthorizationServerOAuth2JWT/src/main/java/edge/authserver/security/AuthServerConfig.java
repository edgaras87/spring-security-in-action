package edge.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${jwt.symmetric.key}")
	private String jwtSymmetricKey;
	
    @Value("${jwt.asymmetric.password}")
    private String password;

    @Value("${jwt.asymmetric.privateKey}")
    private String privateAsymmetricKey;

    @Value("${jwt.asymmetric.alias}")
    private String alias;
	
	// autowiring jwtAccessTokenConverter because of different implementation
	// when one implementation is used there is no need of this autowiring
	@Autowired
	@Lazy
	public JwtAccessTokenConverter jwtAccessTokenConverter;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
					.withClient("client1")
					.secret("secret1")
					.authorizedGrantTypes("password", "refresh_token")
					.scopes("read");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager)
        		 .tokenStore(tokenStore())
        		 .accessTokenConverter(jwtAccessTokenConverter);		
	}
	
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    //@Bean
    public JwtAccessTokenConverter jwtAccessTokenConverterDummy() {
        var converter = new JwtAccessTokenConverter();
        return converter;
    }
    
    @Profile("symmetric")
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverterSymmetric() {
        var converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSymmetricKey);
        return converter;
    }
    
    @Profile("asymmetric")
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverterAsymmetric() {
        var converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(privateAsymmetricKey),
                        password.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return converter;
        
    }

}