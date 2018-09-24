package example.bdd.shop.acceptance.test;

import static example.bdd.shop.acceptance.test.util.Randoms.randomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import example.bdd.shop.resource.dto.CategoryDTO;

@Component
public class ViewAllCategoriesSteps extends AbstractStorySteps {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, Map<String, String>> idAndCategoryRowMap;
	private List<CategoryDTO> returnedCategories;

	@Override
	protected void setUp() {
		idAndCategoryRowMap = new HashMap<>();
	}

	@Override
	protected void tearDown() {
		idAndCategoryRowMap = null;
		returnedCategories = null;
	}

	@Given("the following categories have been defined: $categories")
	public void givenSomeCategoriesHaveBeenDefined(
			final ExamplesTable categories) {
		categories
				.getRows()
				.forEach(
						categoryRow -> {
							final String id = randomString(10);
							jdbcTemplate.execute(String
									.format("INSERT INTO category (id, name, description) VALUES ('%s', '%s', '%s')",
											id, categoryRow.get("name"),
											categoryRow.get("description")));
							idAndCategoryRowMap.put(id, categoryRow);
						});
	}

	@When("an anonymous user searches for all available categories")
	public void whenAnAnonymousUser() throws Exception {
		final MvcResult result = mockMvc.perform(get("/categories"))
				.andExpect(status().isOk()).andReturn();
		returnedCategories = new Gson().fromJson(result.getResponse()
				.getContentAsString(), new TypeToken<List<CategoryDTO>>() {
		}.getType());
	}

	@Then("a list of all categories is displayed")
	public void thenTheListOfAllCategoriesIsDisplayed() {
		assertThat(returnedCategories.size(), is(idAndCategoryRowMap.size()));
		returnedCategories.forEach(categoryDto -> {
			final Map<String, String> values = idAndCategoryRowMap
					.get(categoryDto.getId());
			assertThat(values, is(notNullValue()));
			assertThat(categoryDto.getName(), is(values.get("name")));
			assertThat(categoryDto.getDescription(),
					is(values.get("description")));
		});
	}

}
