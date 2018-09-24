package example.bdd.shop.acceptance.test;

import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.SilentlyAbsorbingFailure;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AbstractStory.class })
@WebAppConfiguration
@ActiveProfiles("test")
@Configuration
@ComponentScan("example.bdd.shop")
public abstract class AbstractStory extends JUnitStory {

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new InstanceStepsFactory(configuration(), steps());
	}

	protected abstract AbstractStorySteps steps();

	@Override
	public org.jbehave.core.configuration.Configuration configuration() {
		if (super.hasConfiguration()) {
			return super.configuration();
		}

		return new MostUsefulConfiguration()
		     .usePendingStepStrategy(new FailingUponPendingStep())
		     .useFailureStrategy(new SilentlyAbsorbingFailure())
				.useStoryReporterBuilder(
						new StoryReporterBuilder()
								.withFailureTrace(true)
								.withFailureTraceCompression(true)
								.withFormats(Format.IDE_CONSOLE,
										Format.CONSOLE, Format.HTML_TEMPLATE)
								.withCodeLocation(
										codeLocationFromPath("build/jbehave")));
	}
}
