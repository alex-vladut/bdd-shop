package example.bdd.shop.acceptance.test;

import static example.bdd.shop.acceptance.test.util.Randoms.randomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import example.bdd.shop.resource.dto.ProductDTO;

@Component
public class CreateProductSteps extends AbstractStorySteps{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String userId;
	private String categoryId;
	private MockHttpServletResponse response;
	
	@Override
	protected void setUp() {
		userId = randomString(10);
		categoryId = randomString(10);
	}

	@Override
	protected void tearDown() {
		userId = null;
		categoryId = null;
		response = null;
	}
	
	@Given("$userName is an admin user")
	public void givenAnAdminUser(@Named("userName") final String userName){
		jdbcTemplate.update("INSERT INTO user (id, first_name, last_name, admin) VALUES (?, ?, ?, ?)", userId, userName, randomString(10), true);
	}
	
	@Given("the category '$categoryName' is defined")
	public void givenACategoryIsDefined(@Named("categoryName") final String categoryName){
		jdbcTemplate.update("INSERT INTO category (id, name, description) VALUES (?, ?, ?)", categoryId, categoryName, randomString(20));
	}
	
	@When("John adds a new product with the following details: $productDetails")
	public void whenTheAdminUserAddsANewProductWithValidDetails(final ExamplesTable productDetails) throws Exception{
		final ProductDTO productDTO = new ProductDTO();
		productDTO.setName(productDetails.getRow(0).get("name"));
		productDTO.setSpecifications(productDetails.getRow(0).get("specifications"));
		if(!productDetails.getRow(0).get("category").isEmpty()){
			productDTO.setCategoryId(categoryId);
		}
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, userId);
		
		response = mockMvc.perform(post(URI.create("/products")).content(new Gson().toJson(productDTO)).headers(httpHeaders).contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
	}
	
	@Then("the product is added successfully")
	public void thenTheProductIsAddedSuccessfully(){
		assertThat(response.getStatus(), is(HttpServletResponse.SC_CREATED));
		final String location = (String) response.getHeaderValue("Location");
		assertThat(location, is(notNullValue()));
		final String createdProductId = location.substring(location.lastIndexOf('/') + 1);
	
		final ProductDTO productDTO = jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", new String[]{ createdProductId }, 
			(rs, rowNum)-> {
				final ProductDTO result = new ProductDTO();
				result.setName(rs.getString("name"));
				result.setSpecifications(rs.getString("specifications"));
				result.setCategoryId(rs.getString("category_id"));
				return result;
			});
		assertThat(productDTO, is(notNullValue()));
		assertThat(productDTO.getCategoryId(), is(categoryId));
	}
	
	@Then("the request is rejected")
	public void thenTheRequestIsRejected(){
		assertThat(response.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));
	}
	

}
