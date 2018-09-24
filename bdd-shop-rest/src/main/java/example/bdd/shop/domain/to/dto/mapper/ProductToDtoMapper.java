package example.bdd.shop.domain.to.dto.mapper;

import org.springframework.stereotype.Component;

import example.bdd.shop.domain.model.Product;
import example.bdd.shop.resource.dto.ProductDTO;

@Component
public class ProductToDtoMapper {
	
	public ProductDTO map(Product product){
		if(product == null){
			return null;
		}
		
		final ProductDTO productDto = new ProductDTO();
		productDto.setId(product.getEntityId().getId());
		productDto.setName(product.getName());
		productDto.setSpecifications(product.getSpecifications());
		productDto.setCategoryId(product.getCategoryId().getId());
		return productDto;
	}

}
