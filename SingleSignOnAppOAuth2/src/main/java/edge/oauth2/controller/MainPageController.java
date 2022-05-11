package edge.oauth2.controller;

import java.util.logging.Logger;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

	private Logger logger = 
		Logger.getLogger(MainPageController.class.getName());
	
	@GetMapping
	public String main(OAuth2AuthenticationToken token) {
		logger.info(String.valueOf(token.getPrincipal()));
		return "main";
	}
	
}
