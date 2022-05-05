package edge.project.data;

import org.springframework.data.jpa.repository.JpaRepository;

import edge.project.module.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
