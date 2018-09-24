package example.bdd.shop.acceptance.test;

import org.springframework.beans.factory.annotation.Autowired;

public class CreateProduct extends AbstractStory {

	@Autowired
	private CreateProductSteps steps;
	
	@Override
	protected CreateProductSteps steps() {
		return steps;
	}

}
