package example.bdd.shop.acceptance.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.ScenarioType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractStorySteps {

	@Autowired
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@BeforeScenario(uponType = ScenarioType.ANY)
	public void setUpGlobal() {
		assertThat(webApplicationContext, is(notNullValue()));
		mockMvc = webAppContextSetup(webApplicationContext).build();

		setUp();
	}

	protected abstract void setUp();

	@AfterScenario(uponType = ScenarioType.ANY, uponOutcome = Outcome.ANY)
	public void tearDownGlobal() {
		mockMvc = null;

		tearDown();
	}

	protected abstract void tearDown();

}
