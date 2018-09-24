package example.bdd.shop.persistence.jdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import example.bdd.shop.domain.model.Category;
import example.bdd.shop.domain.model.EntityId;
import example.bdd.shop.persistence.CategoryRepository;
import example.bdd.shop.persistence.exception.PersistenceException;

public class JdbcCategoryRepositoryTest {

	private JdbcTemplate jdbcTemplateMock;
	private CategoryRepository categoryRepository;

	@Before
	public void setUp() {
		jdbcTemplateMock = mock(JdbcTemplate.class);
		categoryRepository = new JdbcCategoryRepository(jdbcTemplateMock);
	}

	@Test
	public void shouldFindCategoryById_withExistingId() {
		// Given
		final EntityId categoryId = new EntityId();
		final Category entity = mock(Category.class);
		given(jdbcTemplateMock.queryForObject(anyString(), any(JdbcCategoryRepository.class), eq(categoryId.getId())))
				.willReturn(entity);

		// When
		final Category result = categoryRepository.findById(categoryId);
		
		// Then
		assertThat(result, is(entity));
	}
	
	@Test
	public void shouldNotFindCategoryById_withNonExistentId() {
		// Given
		final EntityId categoryId = new EntityId();
		given(jdbcTemplateMock.queryForObject(anyString(), any(JdbcCategoryRepository.class), eq(categoryId.getId())))
				.willReturn(null);

		// When
		final Category result = categoryRepository.findById(categoryId);
		
		// Then
		assertThat(result, is(nullValue()));
	}
	
	@Test
	public void shouldNotFindCategoryById_withPersistenceExceptionThrown() {
		// Given
		final EntityId categoryId = new EntityId();
		final DataAccessException exception = mock(DataAccessException.class);
		given(jdbcTemplateMock.queryForObject(anyString(), any(JdbcCategoryRepository.class), eq(categoryId.getId())))
				.willThrow(exception);

		// When
		PersistenceException thrownException = null;
		try{
			categoryRepository.findById(categoryId);
		}catch(final PersistenceException e){
			thrownException = e;
		}
		
		// Then
		assertThat(thrownException, is(notNullValue()));
	}
}
