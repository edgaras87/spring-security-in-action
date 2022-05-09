package edge.csrf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class HomeController {

	@GetMapping(path = "/home")
	public String homeGet() {
		return "get home works!!!";
	}
	
	@PostMapping(path = "/home")
	public String homePost() {
		return "post home works!!!";
	}
	
	@PostMapping(path = "/ciao")
	public String ciaoPost() {
		return "post ciao works!!!";
	}
	
	@PostMapping(path = "/hola")
	public String holaPost() {
		return "post hola works!!!";
	}
	
}
