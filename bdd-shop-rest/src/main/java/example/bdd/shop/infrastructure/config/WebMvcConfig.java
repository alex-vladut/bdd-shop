package example.bdd.shop.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@EnableWebMvc
@Configuration
@ComponentScan({ "example.bdd.shop" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureContentNegotiation(
			final ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(true);
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureViewResolvers(final ViewResolverRegistry registry) {
		super.configureViewResolvers(registry);
		registry.enableContentNegotiation(new MappingJackson2JsonView());
	}

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
