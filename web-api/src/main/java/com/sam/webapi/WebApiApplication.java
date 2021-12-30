package com.sam.webapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				var corsRegistration = registry.addMapping("/**");

				var allowedOrigins = env.getProperty("cors.allowedOrigins");
				for (var allowedOrigin : allowedOrigins.split(","))
					corsRegistration.allowedOrigins(allowedOrigin);

				corsRegistration.allowedMethods(env.getProperty("cors.allowedMethods"));
			}
		};
	}



}
