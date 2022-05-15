package com.sam.webapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Value("${cors.allowedOrigins}")
	private List<String> corsAllowedOrigins;

	@Value("${cors.allowedMethods}")
	private List<String> corsAllowedMethods;

	private static final String[] AUTH_ALLOWED_LIST = {
			"/authenticate",
			"/tournaments/**",
			"/teams/**",
			"/players/**",
			// Swagger UI v2
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			// Swagger UI v3 (OpenAPI)
			"/v3/api-docs/**",
			"/swagger-ui/**"
	};

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		var jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

		// Disable Cross-Site Request Forgery
		httpSecurity.csrf().disable()
				// dont authenticate these particular requests
				.authorizeRequests().antMatchers(AUTH_ALLOWED_LIST).permitAll()
				// all other requests need to be authenticated
				.anyRequest().authenticated().and()
				.cors().configurationSource(corsConfigurationSource()).and()
				// make sure we use stateless session
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	CorsConfigurationSource corsConfigurationSource() {
		var corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedMethods(corsAllowedMethods);
		corsConfiguration.setAllowedOrigins(corsAllowedOrigins);

		var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		return urlBasedCorsConfigurationSource;
	}


}