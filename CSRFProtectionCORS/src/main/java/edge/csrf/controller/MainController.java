package edge.csrf.controller;

import java.util.logging.Logger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	private Logger logger = Logger.getLogger(MainController.class.getName());

	@Profile("CSRF")
	@GetMapping
	public String homeGet() {
		return "main";
	}
	
	@Profile("CORS")
	@GetMapping(path = "/mainCORS")
	public String homeGetCORS() {
		return "mainCORS";
	}
	
	@Profile("CORS")
	@PostMapping(path = "/test")
	@ResponseBody
	@CrossOrigin("http://localhost:8080")
	public String test() {
		logger.info("Test method called.");
		return "HELLO";
	}
	
}
