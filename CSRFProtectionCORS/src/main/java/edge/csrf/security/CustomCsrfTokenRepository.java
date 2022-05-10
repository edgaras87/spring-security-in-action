package edge.csrf.security;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

public class CustomCsrfTokenRepository implements CsrfTokenRepository {

	@Autowired
	private JpaTokenRepository tokenRepo;
	
	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString();
		
		return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getHeader("X-IDENTIFIER");
		
		System.out.println(identifier);
		
		Optional<Token> existingToken = tokenRepo.findTokenByIdentifier(identifier);
		
		System.out.println(existingToken.isPresent());
		
		if(existingToken.isPresent()) {
			Token token = existingToken.get();
			token.setToken(csrfToken.getToken());
		} else {
			Token token = new Token();
			token.setToken(csrfToken.getToken());
			token.setIdentifier(identifier);
			tokenRepo.save(token);
		}
		
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		
		String identifier = request.getHeader("X-IDENTIFIER");
		Optional<Token> existingToken = tokenRepo.findTokenByIdentifier(identifier);
		
		if (existingToken.isPresent()) {
			Token token = existingToken.get();
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
		}
		
		return null;
	}

}
