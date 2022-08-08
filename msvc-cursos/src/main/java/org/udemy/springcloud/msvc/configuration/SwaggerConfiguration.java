package org.udemy.springcloud.msvc.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("udemy").packagesToScan("org.udemy.springcloud.msvc").build();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API DE UDEMY PARA DOCKER: CURSOS").description("Microservicio de cursos").version("v0.0.1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("GOOGLE").url("https://www.google.com"));
	}
}
