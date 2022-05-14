package edge.resourceserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${jwt.symmetric.key}")
	private String jwtSymmetricKey;
	
    @Value("${jwt.asymmetric.publicKey}")
    private String publicAsymmetricKey;
	
	// autowiring jwtAccessTokenConverter because of different implementation
	// when one implementation is used there is no need of this autowiring
	@Autowired
	@Lazy
	public JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
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
        converter.setVerifierKey(publicAsymmetricKey);
        return converter;
    }
    
}