package example.bdd.shop.persistence;

import java.util.List;

import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.domain.model.Product;

public interface ProductRepository {

	EntityId save(Product product);
	
	List<Product> findByCategory(String categoryId);

}
