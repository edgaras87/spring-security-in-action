package edge.resourceserver.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class ResourceServerConfigMigration extends WebSecurityConfigurerAdapter {

	@Value("${jwt.symmetric.key}")
	private String jwtSymmetricKey;
	
    @Value("${jwt.asymmetric.publicKey}")
    private String publicAsymmetricKey;
    
	@Autowired
	@Lazy
    private JwtDecoder jwtDecoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .oauth2ResourceServer(c -> c.jwt( jwt -> {
                    jwt.decoder(jwtDecoder);
                }));
    }

    @Profile("symmetric")
    @Bean
    public JwtDecoder jwtDecoderSymmteric() {
        byte [] key = jwtSymmetricKey.getBytes();
        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(originalKey)
                									  .build();

        return jwtDecoder;
    }
    
    @Profile("asymmetric")
    @Bean
    public JwtDecoder jwtDecoderAsymmetric() {
        try {
        	
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            trimPublicKeySubtitles();
            var key = Base64.getDecoder().decode(publicAsymmetricKey);

            var x509 = new X509EncodedKeySpec(key);
            var rsaKey = (RSAPublicKey) keyFactory.generatePublic(x509);
            return NimbusJwtDecoder.withPublicKey(rsaKey).build();
        } catch (Exception e) {
            throw new RuntimeException("Wrong public key");
        }
    }
    
    private void trimPublicKeySubtitles() {
        String begin = "-----BEGIN PUBLIC KEY-----";
        String end   = "-----END PUBLIC KEY-----";
        if (publicAsymmetricKey.startsWith("-----BEGIN PUBLIC KEY-----")) {
        	publicAsymmetricKey = publicAsymmetricKey.substring(begin.length());
        	publicAsymmetricKey = publicAsymmetricKey.substring(0, publicAsymmetricKey.length() - end.length());
        }
    }
    
}