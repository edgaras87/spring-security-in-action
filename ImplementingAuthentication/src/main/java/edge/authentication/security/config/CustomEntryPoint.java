package edge.authentication.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, 
						 HttpServletResponse response,
						 AuthenticationException authException) 
								 throws IOException, ServletException {
		response.addHeader("message", "Luke, I am your father!");
		// error code (UNAUTHORIZED 401) 
		response.sendError(HttpStatus.UNAUTHORIZED.value());
		//response.sendError(666);
		
		
	}

}
