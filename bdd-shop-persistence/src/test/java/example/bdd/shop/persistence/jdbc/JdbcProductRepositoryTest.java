package example.bdd.shop.persistence.jdbc;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcProductRepositoryTest {

	private JdbcProductRepository productRepository;
	private JdbcTemplate jdbcTemplateMock;
	
	@Before
	public void setUp(){
		jdbcTemplateMock= mock(JdbcTemplate.class);
		productRepository = new JdbcProductRepository(jdbcTemplateMock);
	}
	
	@Test
	public void shouldFindAllProducts_withCategoryId(){
		//Given
		
		//When
		String[] st = {};
		Object[] a = st;
		if(st instanceof Object[]){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
		
		//Then
	}
}
