package edge.security.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
	
	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("encoding:");
		System.out.println("\t before encoding - " + rawPassword.toString());
		String encoded = customEncoding(rawPassword.toString());
		System.out.println("\t after  encoding - " + encoded);
		return encoded;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		System.out.println("decoding:");
		System.out.println(" === encoding entered password === ");
		String encodedRaw = encode(rawPassword);
		System.out.println(" ================================= ");
		System.out.println("\t      encoded password: " + encodedPassword.toString());
		System.out.println("\t encoded(raw) password: " + encodedRaw);
		/**
		 *  There is no need to check this, encodedPassword is already 
		 *  coming truncated by DelegatingPasswordEncoder.
		 */
//		if (encodedPassword.startsWith("{my}"))
//			encodedRaw = "{my}" + encodedRaw; 
		
		return encodedRaw.equals(encodedPassword);
	}
	
	// No encoding at all
	private String plainTextPassword(String input) {
		return input;
	}

	// My simple encoder
	private String customEncoding(String input) {
		return "custom_" + input;
	}

	// SHA512
	private String hashWithSHA512(String input) {
		StringBuilder result = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] digested = md.digest(input.getBytes());
			for (int i = 0; i < digested.length; i++) {
				result.append(Integer.toHexString(0xFF & digested[i]));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Bad algorithm");
		}
		return result.toString();
	}

}
