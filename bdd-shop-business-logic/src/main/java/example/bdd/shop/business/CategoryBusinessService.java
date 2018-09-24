package example.bdd.shop.business;

import java.util.List;

import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.Product;

public interface CategoryBusinessService {

	List<Category> findAllCategories();

	List<Product> getProducts(String categoryId);
}
