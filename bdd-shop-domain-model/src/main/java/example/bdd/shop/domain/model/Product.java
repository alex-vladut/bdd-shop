package example.bdd.shop.domain.model;

public class Product extends Entity {

	private String name;
	private String specifications;
	private EntityId categoryId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public EntityId getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(EntityId categoryId) {
		this.categoryId = categoryId;
	}

}
