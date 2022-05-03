package edge.security.config;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomKeyPasswordEncryptor implements PasswordEncoder {

	private TextEncryptor t;
	private BytesEncryptor b;
	private String salt;
	private String secret;
	
	public CustomKeyPasswordEncryptor() {
		this.salt = KeyGenerators.string().generateKey();
		this.secret = KeyGenerators.string().generateKey();
		t = Encryptors.delux(secret, salt);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return t.encrypt(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.equals(t.decrypt(encodedPassword));
	}

}
