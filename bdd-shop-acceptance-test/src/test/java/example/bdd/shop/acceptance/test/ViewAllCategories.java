package example.bdd.shop.acceptance.test;

import org.springframework.beans.factory.annotation.Autowired;

public class ViewAllCategories extends AbstractStory {

	@Autowired
	private ViewAllCategoriesSteps steps;
	
	@Override
	protected ViewAllCategoriesSteps steps() {
		return steps;
	}
	
}
