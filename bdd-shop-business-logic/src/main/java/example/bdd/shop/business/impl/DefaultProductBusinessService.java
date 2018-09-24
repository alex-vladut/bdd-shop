package example.bdd.shop.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.bdd.shop.business.ProductBusinessService;
import example.bdd.shop.business.exception.SecurityViolationException;
import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.domain.model.User;
import example.bdd.shop.persistence.ProductRepository;
import example.bdd.shop.persistence.UserRepository;

@Component
public class DefaultProductBusinessService implements ProductBusinessService {

	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public DefaultProductBusinessService(final ProductRepository productRepository,
			final UserRepository userRepository) {
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public String createProduct(final String creatorId, final String name, final String specifications, final String categoryId) {
		final User creator = userRepository.findById(new EntityId(creatorId));
		if(!creator.isAdmin()){
			throw new SecurityViolationException("User does not have the permission to create a product.");
		}
		if(atLeastOneNullOrEmpty(name, specifications, categoryId)){
			throw new IllegalArgumentException("Missing product attribute.");
		}
		final Product product = new Product();
		product.setName(name);
		product.setSpecifications(specifications);
		product.setCategoryId(new EntityId(categoryId));
		final EntityId productId = productRepository.save(product);
		return productId.getId();
	}
	
	private boolean atLeastOneNullOrEmpty(final String... strings){
		for(String string : strings){
			if(string == null || string.isEmpty()){
				return true;
			}
		}
		return false;
	}
	

}
