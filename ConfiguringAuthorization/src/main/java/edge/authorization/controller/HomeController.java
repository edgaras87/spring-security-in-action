package edge.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/ciao")
	public String ciao() {
		return "ciao";
	}
	
	@GetMapping("/hola")
	public String hola() {
		return "hola";
	}
	
}
