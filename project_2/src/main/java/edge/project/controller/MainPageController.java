package edge.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edge.project.service.ProductService;

@Controller
public class MainPageController {
	
	private ProductService productService;
	
	@Autowired
	public MainPageController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/main")
	public String main(Authentication auth, Model model) {
		model.addAttribute("username", auth.getName());
		model.addAttribute("products", productService.findAll());
		return "main";
	}
	
}
