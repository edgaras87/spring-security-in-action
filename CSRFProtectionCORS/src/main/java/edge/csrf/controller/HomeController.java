package edge.csrf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

	@GetMapping
	public String homeGet() {
		return "get home works!!!";
	}
	
	@PostMapping
	public String homePost() {
		return "post home works!!!";
	}
	
}
