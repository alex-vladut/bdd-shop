package example.bdd.shop.domain.model;

import java.util.UUID;

public class EntityId {

	private String id;
	
	public EntityId(final String id) {
		if(id == null){
			throw new IllegalArgumentException("The id should not be null.");
		}
		this.id = id;
	}
	
	public EntityId() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	
}
