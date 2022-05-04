package edge.authentication.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) 
					throws IOException, ServletException {
		var authorities =  authentication.getAuthorities();
		
		var auth = authorities.stream()
							  .filter(a -> a.getAuthority().equals("read"))
							  .findFirst();
		if (auth.isPresent())
			response.sendRedirect("/page");
		else
			response.sendRedirect("/error");
	}

}
