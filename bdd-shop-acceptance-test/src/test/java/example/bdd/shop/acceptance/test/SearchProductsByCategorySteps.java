package example.bdd.shop.acceptance.test;

import static example.bdd.shop.acceptance.test.util.Randoms.randomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import example.bdd.shop.resource.dto.ProductDTO;

@Component
public class SearchProductsByCategorySteps extends AbstractStorySteps {
	private static final String INSERT_CATEGORY_SQL = "INSERT INTO category (id, name, description) VALUES (?, ?, ?)";
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (id, name, specifications, category_id) VALUES (?, ?, ?, ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, String> categoryNameAndIdMap;
	private Map<String, String> productNameAndIdMap;
	private MvcResult result;

	@Override
	protected void setUp() {
		categoryNameAndIdMap = new HashMap<>();
		productNameAndIdMap = new HashMap<>();
	}

	@Override
	protected void tearDown() {
		categoryNameAndIdMap = null;
		productNameAndIdMap = null;
		result = null;
	}

	@Given("the categories \"$firstCategoryName\" and \"$secondCategoryName\" are defined")
	public void givenTwoCategoriesAreDefined(
			@Named("firstCategoryName") final String firstCategoryName,
			@Named("secondCategoryName") final String secondCategoryName) {
		addCategory(firstCategoryName);
		addCategory(secondCategoryName);
	}

	@Given("the following products exist in the system: $products")
	public void givenSomeProductsExistInTheSystem(final ExamplesTable products) {
		products.getRows().forEach(this::addProduct);
	}

	@When("a user searches all the products in the \"$categoryName\" category")
	public void whenAUserSearchesAllTheProductsInACategory(
			@Named("categoryName") final String categoryName) throws Exception {
		final String categoryId = categoryNameAndIdMap.get(categoryName);
		result = mockMvc
				.perform(get("/categories/{categoryId}/products", categoryId))
				.andExpect(status().isOk()).andReturn();
	}

	@Then("the following products are returned: $expectedProducts")
	public void thenTheProductsAreReturned(final ExamplesTable expectedProducts)
			throws JsonSyntaxException, UnsupportedEncodingException {
		final List<ProductDTO> returnedProducts = new Gson().fromJson(result.getResponse().getContentAsString(), new TypeToken<List<ProductDTO>>() {}.getType());
	    assertThat(returnedProducts, is(notNullValue()));
	    assertThat(returnedProducts.size(), is(expectedProducts.getRows().size()));
	    expectedProducts.getRows().forEach(productRow -> {
	    	boolean matchExpectedProduct = returnedProducts.stream().anyMatch(product -> {
	    		return product.getName().equals(productRow.get("Name")) && product.getSpecifications().equals(productRow.get("Specifications"));
	    	});
	    	assertThat(matchExpectedProduct, is(true));
	    });
	}

	private void addProduct(Map<String, String> productRow) {
		final String productId = randomString(10);
		final String productName = productRow.get("Name");
		jdbcTemplate.update(INSERT_PRODUCT_SQL, (ps) -> {
			ps.setString(1, productId);
			ps.setString(2, productName);
			ps.setString(3, productRow.get("Specifications"));
			final String categoryName = productRow.get("Category");
			ps.setString(4, categoryNameAndIdMap.get(categoryName));
		});
		productNameAndIdMap.put(productName, productId);
	}

	private void addCategory(final String categoryName) {
		final String categoryId = randomString(10);
		jdbcTemplate.update(INSERT_CATEGORY_SQL, (ps) -> {
			ps.setString(1, categoryId);
			ps.setString(2, categoryName);
			ps.setString(3, randomString(10));
		});
		categoryNameAndIdMap.put(categoryName, categoryId);
	}

}
