package example.bdd.shop.persistence.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test")
public class PersistenceConfiguration {

	@Bean
	public JdbcTemplate jdbcTemplate(){
		final EmbeddedDatabase db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema.sql")/*.addScript("classpath:test-data.sql")*/.build();
		return new JdbcTemplate(db);
	}
}
