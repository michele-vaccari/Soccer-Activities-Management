package com.sam.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
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
