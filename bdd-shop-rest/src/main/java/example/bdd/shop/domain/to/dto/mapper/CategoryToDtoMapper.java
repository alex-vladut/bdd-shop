package example.bdd.shop.domain.to.dto.mapper;

import org.springframework.stereotype.Component;

import example.bdd.shop.domain.model.Category;
import example.bdd.shop.resource.dto.CategoryDTO;

@Component
public class CategoryToDtoMapper {

	public CategoryDTO map(final Category category){
		if(category == null){
			return null;
		}
		
		final CategoryDTO result = new CategoryDTO();
		result.setId(category.getEntityId().getId());
		result.setName(category.getName());
		result.setDescription(category.getDescription());
		return result;
	}
}
