package example.bdd.shop.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import example.bdd.shop.business.CategoryBusinessService;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.domain.to.dto.mapper.CategoryToDtoMapper;
import example.bdd.shop.domain.to.dto.mapper.ProductToDtoMapper;
import example.bdd.shop.resource.dto.CategoryDTO;
import example.bdd.shop.resource.dto.ProductDTO;

@RestController
@RequestMapping("/categories")
public class CategoriesResource {

	private final CategoryBusinessService categoryBusinessService;
	private final CategoryToDtoMapper categoryToDtoMapper;
	private final ProductToDtoMapper productToDtoMapper;

	@Autowired
	public CategoriesResource(
			final CategoryBusinessService categoryBusinessService,
			final CategoryToDtoMapper categoryToDtoMapper,
			final ProductToDtoMapper productToDtoMapper) {
		this.categoryBusinessService = categoryBusinessService;
		this.categoryToDtoMapper = categoryToDtoMapper;
		this.productToDtoMapper = productToDtoMapper;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<CategoryDTO> getAllCategories() {
		return categoryBusinessService.findAllCategories().stream()
				.map(categoryToDtoMapper::map).collect(Collectors.toList());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{categoryId}/products")
	public List<ProductDTO> getProducts(@PathVariable("categoryId") final String categoryId) {
		final List<Product> products = categoryBusinessService.getProducts(categoryId);
		return products.stream().map(productToDtoMapper::map).collect(Collectors.toList());
	}
	
}
