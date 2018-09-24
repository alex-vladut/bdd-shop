package example.bdd.shop.resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.bdd.shop.business.CategoryBusinessService;
import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.domain.to.dto.mapper.CategoryToDtoMapper;
import example.bdd.shop.domain.to.dto.mapper.ProductToDtoMapper;
import example.bdd.shop.resource.dto.CategoryDTO;
import example.bdd.shop.resource.dto.ProductDTO;

public class CategoriesResourceTest {

	private CategoryBusinessService categoryBusinessServiceMock;
	private CategoryToDtoMapper categoryToDtoMapperMock;
	private ProductToDtoMapper productDomainToDtoMapperMock;
	private CategoriesResource categoriesResource;

	@Before
	public void setUp() {
		categoryToDtoMapperMock = mock(CategoryToDtoMapper.class);
		categoryBusinessServiceMock = mock(CategoryBusinessService.class);
		productDomainToDtoMapperMock = mock(ProductToDtoMapper.class);
		categoriesResource = new CategoriesResource(categoryBusinessServiceMock, categoryToDtoMapperMock, productDomainToDtoMapperMock);
	}

	@After
	public void tearDown() {
		categoriesResource = null;
	}

	@Test
	public void shouldReturnAListOfProducts_withValidCategoryId() {
		// Given
		final String categoryId = RandomStringUtils.random(10);
		final List<Product> products = randomListOfMocks(Product.class);
		
		given(categoryBusinessServiceMock.getProducts(categoryId)).willReturn(products);
		
		final List<ProductDTO> productDtos = new ArrayList<>();
		products.forEach(product ->{
			final ProductDTO productDto = mock(ProductDTO.class);
			given(productDomainToDtoMapperMock.map(product)).willReturn(productDto );
			productDtos.add(productDto);
		});
		
		// When
		final List<ProductDTO> result = categoriesResource.getProducts(categoryId);

		// Then
		assertThat(result, is(notNullValue()));
		assertThat(result, equalTo(productDtos));
	}
	
	@Test
	public void shouldReturnAllCategories(){
		//Given
		final List<Category> categories = randomListOfMocks(Category.class);
		
		given(categoryBusinessServiceMock.findAllCategories()).willReturn(categories);
		
		final List<CategoryDTO> categoryDtos = new ArrayList<>();
		categories.forEach(category -> {
			final CategoryDTO categoryDto = mock(CategoryDTO.class);
			given(categoryToDtoMapperMock.map(category)).willReturn(categoryDto);
			categoryDtos.add(categoryDto);
		});
		
		//When
		final List<CategoryDTO> result = categoriesResource.getAllCategories();
		
		//Then
		assertThat(result, is(notNullValue()));
		assertThat(result, equalTo(categoryDtos));
	}
	
	private <T> List<T> randomListOfMocks(final Class<T> clazz){
		final int numberOfElements = RandomUtils.nextInt(1, 10);
		return IntStream.rangeClosed(0, numberOfElements).mapToObj(i -> {
			return mock(clazz);
		}).collect(Collectors.toList());
	}

}
