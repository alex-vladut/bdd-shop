package example.bdd.shop.persistence;

import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.domain.model.User;

public interface UserRepository {

	User findById(EntityId id);
}
