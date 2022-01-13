package com.sam.webapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Soccer Activities Management API",
				version = "1.0",
				description = "Soccer Activities Management API",
				license = @License(name = "MIT License"),
				contact = @Contact(url = "https://www.linkedin.com/in/michele-vaccari",
								   name = "Michele Vaccari",
								   email = "michele.vaccari@outlook.com")
		),
		servers = {
				@Server(
						description = "Development server",
						url = "http://localhost:8080"
						)
		}
)
public class WebApiApplication {

	@Value("${cors.allowedOrigins}")
	private List<String> corsAllowedOrigins;

	@Value("${cors.allowedMethods}")
	private List<String> corsAllowedMethods;

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("Bearer",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT").description("JWT Authorization header using the Bearer scheme.")));
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				var corsRegistration = registry.addMapping("/**");

				for (var corsAllowedOrigin : corsAllowedOrigins)
					corsRegistration.allowedOrigins(corsAllowedOrigin);

				for (var corsAllowedMethod : corsAllowedMethods)
					corsRegistration.allowedMethods(corsAllowedMethod);
			}
		};
	}



}
