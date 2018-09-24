package example.bdd.shop.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.bdd.shop.business.CategoryBusinessService;
import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.persistence.CategoryRepository;
import example.bdd.shop.persistence.ProductRepository;

@Service
public class DefaultCategoryBusinessService implements CategoryBusinessService {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public DefaultCategoryBusinessService(final CategoryRepository categoryRepository,
			final ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}
	
	public List<Category> findAllCategories() {
		return categoryRepository.findAllCategories();
	}
	
	@Override
	public List<Product> getProducts(String categoryId) {
		return productRepository.findByCategory(categoryId);
	}

}
