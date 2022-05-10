package edge.project3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PostMapping("/test")
	public String testPost() {
		return "get test";
	}
	
	@GetMapping("/test")
	public String testGet() {
		return "post test";
	}
	
}
