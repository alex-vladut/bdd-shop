package example.bdd.shop.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.domain.model.User;
import example.bdd.shop.persistence.UserRepository;
import example.bdd.shop.persistence.exception.PersistenceException;

@Repository
public class JdbcUserRepository implements UserRepository, RowMapper<User> {
	
	private static final String FIND_USER_BY_ID_SQL = "SELECT * FROM user WHERE id = ?";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcUserRepository(final JdbcTemplate jdbcTemplate) {
		if (jdbcTemplate == null) {
			throw new IllegalArgumentException(
					"JdbcTemplate parameter should not be null.");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public User findById(final EntityId userId) {
		try {
			return jdbcTemplate.queryForObject(FIND_USER_BY_ID_SQL,
					this, userId.getId());
		} catch (final DataAccessException e) {
			throw new PersistenceException(e.getLocalizedMessage());
		}
	}
	
	@Override
	public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final User user = new User();
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setAdmin(rs.getBoolean("admin"));
		return user;
	}

}
