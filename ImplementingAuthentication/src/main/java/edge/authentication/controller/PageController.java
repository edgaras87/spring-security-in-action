package edge.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping(path = "/page")
	public String page() {
		// will be searched in resources/static
		return "page.html"; 
		// will be searched in resources/templates if thymeleaf dependency is included
		//return "page"; 
	}
	
}
