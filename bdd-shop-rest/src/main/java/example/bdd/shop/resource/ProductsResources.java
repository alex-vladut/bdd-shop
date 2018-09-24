package example.bdd.shop.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import example.bdd.shop.business.ProductBusinessService;
import example.bdd.shop.business.exception.SecurityViolationException;
import example.bdd.shop.resource.dto.ProductDTO;

@RestController
@RequestMapping("/products")
public class ProductsResources {
	
	private final ProductBusinessService productBusinessService;
	
	@Autowired
	public ProductsResources(final ProductBusinessService productBusinessService) {
		this.productBusinessService = productBusinessService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createProduct(@RequestBody final ProductDTO productDto, @RequestHeader(HttpHeaders.AUTHORIZATION) final String userId){
		try{
			final String productId = productBusinessService.createProduct(userId, productDto.getName(), productDto.getSpecifications(), productDto.getCategoryId());
			final HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/products/" + productId));
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}catch (final IllegalArgumentException e){
			return ResponseEntity.badRequest().build();
		}catch (SecurityViolationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
	}
	
}
