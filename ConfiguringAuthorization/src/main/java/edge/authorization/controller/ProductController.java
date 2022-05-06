package edge.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@GetMapping("/product/{code}")
	public String productCode(@PathVariable String code) {
		return code;
	}
	
	@GetMapping("/video/{country}/{language}")
	public String productEmail(@PathVariable String country,
							   @PathVariable String language) {
		return "Video allowed for : " + country + " " + language;
	}
	
}
