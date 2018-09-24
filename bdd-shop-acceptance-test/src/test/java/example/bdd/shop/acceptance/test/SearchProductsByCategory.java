package example.bdd.shop.acceptance.test;

import org.springframework.beans.factory.annotation.Autowired;

public class SearchProductsByCategory extends AbstractStory {

	@Autowired
	private SearchProductsByCategorySteps steps;

	@Override
	protected SearchProductsByCategorySteps steps() {
		return steps;
	}

}
