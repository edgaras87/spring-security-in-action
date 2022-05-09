package edge.authorization.security.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;



public class AuthenticationLoggingFilter extends OncePerRequestFilter {
	
	private final Logger logger = Logger.getLogger(
					AuthenticationLoggingFilter.class.getName());

	@Override
	public void doFilterInternal(HttpServletRequest request, 
								 HttpServletResponse response,
								 FilterChain chain)
			throws IOException, ServletException {
		var httpRequest = request;
		
		String requestId = httpRequest.getHeader("Request-Id");
		
		logger.info("Successfully authenticated request with id " + requestId);
	
		chain.doFilter(request, response);
		
	}
	
}
