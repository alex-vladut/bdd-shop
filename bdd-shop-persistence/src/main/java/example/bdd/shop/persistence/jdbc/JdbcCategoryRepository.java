package example.bdd.shop.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.persistence.CategoryRepository;
import example.bdd.shop.persistence.exception.PersistenceException;

@Repository
public class JdbcCategoryRepository implements CategoryRepository, RowMapper<Category> {

	private static final String INSERT_CATEGORY_SQL = "INSERT INTO category (id, name, description) VALUES (?, ?, ?)";
	private static final String UPDATE_CATEGORY_SQL = "UPDATE category SET name = ?, description = ? WHERE id = ?";
	private static final String DELETE_CATEGORY_SQL = "DELETE category WHERE id = ?";
	private static final String FIND_CATEGORY_BY_ID_SQL = "SELECT * FROM category WHERE id = ?";
	private static final String FIND_ALL_CATEGORIES = "SELECT * FROM category";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCategoryRepository(final JdbcTemplate jdbcTemplate) {
		if (jdbcTemplate == null) {
			throw new IllegalArgumentException(
					"JdbcTemplate parameter should not be null.");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Category findById(final EntityId categoryId) {
		try {
			return jdbcTemplate.queryForObject(FIND_CATEGORY_BY_ID_SQL,
					this, categoryId.getId());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}

	@Override
	public List<Category> findAllCategories() {
		try {
			return jdbcTemplate.query(FIND_ALL_CATEGORIES, this);
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}

	@Override
	public EntityId save(final Category category) {
		category.setEntityId(new EntityId());
		try {
			jdbcTemplate.update(INSERT_CATEGORY_SQL, category.getEntityId()
					.getId(), category.getName(), category.getDescription());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
		return category.getEntityId();
	}

	@Override
	public void update(final Category category) {
		try {
			jdbcTemplate.update(UPDATE_CATEGORY_SQL, category.getName(),
					category.getDescription(), category.getEntityId().getId());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}

	@Override
	public void delete(final Category category) {
		try {
			jdbcTemplate.update(DELETE_CATEGORY_SQL, category.getEntityId()
					.getId());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		final Category category = new Category();
		category.setEntityId(new EntityId(rs.getString("id")));
		category.setName(rs.getString("name"));
		category.setDescription(rs.getString("description"));
		return category;
	}

}
