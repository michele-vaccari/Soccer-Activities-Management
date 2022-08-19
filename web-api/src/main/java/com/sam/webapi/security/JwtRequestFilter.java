package com.sam.webapi.security;

import com.sam.webapi.security.model.JwtTokenData;
import com.sam.webapi.security.model.SamUserDetailsImpl;
import com.sam.webapi.security.service.JwtService;
import com.sam.webapi.security.service.JwtServiceException;
import com.sam.webapi.security.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		initializeFilter(request);
		chain.doFilter(request, response);
	}

	private void initializeFilter(HttpServletRequest request) {
		var requestAuthorizationHeader = request.getHeader("Authorization");

		if (!requestAuthorizationHeaderContainsBearerToken(requestAuthorizationHeader))
			return;

		var jwtToken = requestAuthorizationHeader.substring(7);

		Optional<JwtTokenData> jwtTokenData;
		try {
			jwtTokenData = jwtService.validateJwt(jwtToken);
		}
		catch (JwtServiceException ex) {
			return;
		}

		if (SecurityContextHolder.getContext().getAuthentication() != null)
			return;

		UserDetails user;
		try {
			user = userDetailsService.loadUserByUsername(jwtTokenData.get().getEmail());
		} catch (UsernameNotFoundException ex) {
			return;
		}

		var samUser = (SamUserDetailsImpl) user;

		if (jwtTokenData.get().getRole().equals(samUser.getRole())) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					samUser, null, samUser.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// After setting the Authentication in the context, we specify
			// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private boolean requestAuthorizationHeaderContainsBearerToken(String requestAuthorizationHeader) {
		return requestAuthorizationHeader != null &&
				!requestAuthorizationHeader.isEmpty() &&
				requestAuthorizationHeader.startsWith("Bearer ");
	}
}
