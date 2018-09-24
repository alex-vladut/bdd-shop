package example.bdd.shop.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.domain.model.Product;
import example.bdd.shop.persistence.ProductRepository;
import example.bdd.shop.persistence.exception.PersistenceException;

@Repository
public class JdbcProductRepository implements ProductRepository, RowMapper<Product> {

	private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (id, name, specifications, category_id) VALUES (?, ?, ?, ?)";
	private static final String FIND_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM product WHERE category_id = ?";
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcProductRepository(final JdbcTemplate jdbcTemplate) {
		if (jdbcTemplate == null) {
			throw new IllegalArgumentException(
					"JdbcTemplate parameter should not be null.");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public EntityId save(final Product product) {
		product.setEntityId(new EntityId());
		try {
			jdbcTemplate.update(INSERT_PRODUCT_SQL, product.getEntityId()
					.getId(), product.getName(), product.getSpecifications(), product.getCategoryId().getId());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
		return product.getEntityId();
	}
	
	@Override
	public List<Product> findByCategory(final String categoryId) {
		try {
			return jdbcTemplate.query(FIND_PRODUCTS_BY_CATEGORY_ID, new String[]{ categoryId }, this);
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}

	@Override
	public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Product product = new Product();
		product.setEntityId(new EntityId(rs.getString("id")));
		product.setName(rs.getString("name"));
		product.setSpecifications(rs.getString("specifications"));
		product.setCategoryId(new EntityId(rs.getString("category_id")));
		return product;
	}

}
