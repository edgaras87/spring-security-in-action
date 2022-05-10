package edge.csrf.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/product")
public class ProductController {
	
	private Logger logger = Logger.getLogger(ProductController.class.getName());
	
	@RequestMapping(path = "/add")
	public String add(@RequestParam String name) {
		logger.info(" ===Adding product: " + name);
		return "main";
	}
	
}
