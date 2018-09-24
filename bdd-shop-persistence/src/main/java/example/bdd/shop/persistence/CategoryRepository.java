package example.bdd.shop.persistence;

import java.util.List;

import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.EntityId;

public interface CategoryRepository {

	Category findById(EntityId categoryId);
	
	List<Category> findAllCategories();
	
	EntityId save(Category category);
	
	void update(Category category);
	
	void delete(Category category);
}
