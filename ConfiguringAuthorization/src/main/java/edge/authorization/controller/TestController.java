package edge.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@PostMapping("/a")
	public String postEndpointA() {
		return "Works! post /a";
	}
	
	@GetMapping("/a")
	public String getEndpointA() {
		return "Works! get /a";
	}
	
	@GetMapping("/a/b")
	public String postEndpointB() {
		return "Works! get /a/b";
	}
	
	@GetMapping("/a/b/c")
	public String postEndpointC() {
		return "Works! get /a/b/c";
	}
	
	@GetMapping("/d")
	public String postEndpointD() {
		return "Works! get /d";
	}
	
}
