package edge.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edge.project.data.ProductRepository;
import edge.project.module.Product;

@Service
public class ProductService {

	private ProductRepository productRepo;

	@Autowired
	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
	public List<Product> findAll() {
		return productRepo.findAll();
	}
	
	
	
}
