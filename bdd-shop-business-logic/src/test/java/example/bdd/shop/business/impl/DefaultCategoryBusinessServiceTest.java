package example.bdd.shop.business.impl;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import example.bdd.shop.business.CategoryBusinessService;
import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.persistence.CategoryRepository;
import example.bdd.shop.persistence.ProductRepository;

public class DefaultCategoryBusinessServiceTest {

	private CategoryRepository categoryRepositoryMock;
	private ProductRepository productRepositoryMock;
	private CategoryBusinessService categoryBusinessService;
	
	@Before
	public void setUp(){
		categoryRepositoryMock = mock(CategoryRepository.class);
		productRepositoryMock = mock(ProductRepository.class);
		categoryBusinessService = new DefaultCategoryBusinessService(categoryRepositoryMock, productRepositoryMock);
	}
	
	@After
	public void tearDown(){
		categoryRepositoryMock = null;
		categoryBusinessService = null;
	}
	
	@Test
	public void shouldFindAllCategories(){
		//Given
		final List<Category> categories = Arrays.asList(mock(Category.class), mock(Category.class));
		
		given(categoryRepositoryMock.findAllCategories()).willReturn(categories);
		
		//When
		final List<Category> result = categoryBusinessService.findAllCategories();
		
		//Then
		assertThat(result, is(categories));
	}
	
	@Test
	public void shouldFindProducts_withCategoryId(){
		//Given
		String categoryId = random(20);
		final List<Product> products = Arrays.asList(mock(Product.class), mock(Product.class));
		
		given(productRepositoryMock.findByCategory(categoryId )).willReturn(products);
		
		//When
		final List<Product> result = categoryBusinessService.getProducts(categoryId);
		
		//Then
		assertThat(result, is(products));
	}
	
	
}
