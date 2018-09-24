package example.bdd.shop.resource;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import example.bdd.shop.business.ProductBusinessService;
import example.bdd.shop.business.exception.SecurityViolationException;
import example.bdd.shop.resource.dto.ProductDTO;

public class ProductsResourcesTest {

	private ProductBusinessService productBusinessServiceMock;
	private ProductsResources productsResources;
	
	@Before
	public void setUp(){
		productBusinessServiceMock = mock(ProductBusinessService.class);
		productsResources = new ProductsResources(productBusinessServiceMock);
	}
	
	@Test
	public void shouldCreateProduct_withProductDetailsAndAuthorizedUser(){
		//Given
		final String userId = random(20, true, true);
		final ProductDTO productDto = randomProductDto();
		final String productId = random(20, true, true);
		
		given(productBusinessServiceMock.createProduct(userId, productDto.getName(), productDto.getSpecifications(), productDto.getCategoryId())).willReturn(productId);
		
		//When
		final ResponseEntity<Void> result = productsResources.createProduct(productDto, userId);
		
		//Then
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		assertThat(result.getHeaders().size(), is(not(0)));
		assertThat(result.getHeaders().getLocation().getRawPath(), equalTo("/products/" + productId));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotCreateProduct_withUnauthorizedUser(){
		//Given
		final String userId = random(20, true, true);
		final ProductDTO productDto = randomProductDto();
		
		given(productBusinessServiceMock.createProduct(userId, productDto.getName(), productDto.getSpecifications(), productDto.getCategoryId())).willThrow(SecurityViolationException.class);
		
		//When
		final ResponseEntity<Void> result = productsResources.createProduct(productDto, userId);
		
		//Then
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotCreateProduct_withAuthorizedUserAndInvalidProductDetails(){
		//Given
		final String userId = random(20, true, true);
		final ProductDTO productDto = randomProductDto();
		
		given(productBusinessServiceMock.createProduct(userId, productDto.getName(), productDto.getSpecifications(), productDto.getCategoryId())).willThrow(IllegalArgumentException.class);
		
		//When
		final ResponseEntity<Void> result = productsResources.createProduct(productDto, userId);
		
		//Then
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	private ProductDTO randomProductDto() {
		final String categoryId = random(20, true, true);
		String specifications = random(20, true, true);
		String productName = random(20, true, true);
		final ProductDTO productDto = new ProductDTO();
		productDto.setCategoryId(categoryId);
		productDto.setName(productName);
		productDto.setSpecifications(specifications);
		return productDto;
	}
	
}
