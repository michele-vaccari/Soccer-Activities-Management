package com.sam.webapi.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sam.webapi.security.utils.PemUtils;
import com.sam.webapi.security.model.JwtTokenData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

	private String issuer = "Soccer Activities Management";
	private long duration = 20 * 60 * 60 * 1000; // 20 hours

	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	private Algorithm algorithm;

	public JwtService(@Value("${jwt.publicKeyPath}") String publicKeyPath,
					  @Value("${jwt.privateKeyPath}") String privateKeyPath) throws JwtServiceException {
		try
		{
			this.publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromFile(publicKeyPath, "RSA");
			this.privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(privateKeyPath, "RSA");
			this.algorithm = Algorithm.RSA256(publicKey, privateKey);
		}
		catch(IOException | JWTCreationException ex) {
			throw new JwtServiceException();
		}
	}

	public String createJwt(String email, Integer id, String role, String name, String surname, int teamId) {
		var currentTime = System.currentTimeMillis();

		return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(new Date(currentTime + duration))
				.withIssuedAt(new Date(currentTime))
				.withClaim("email", email)
				.withClaim("id", id)
				.withClaim("role", role)
				.withClaim("name", name)
				.withClaim("surname", surname)
				.withClaim("teamId", teamId)
				.sign(algorithm);
	}

	public Optional<JwtTokenData> validateJwt(String token) throws JwtServiceException {

		var jwtVerifier = JWT.require(algorithm).build();

		try {
			var decodedJwt = jwtVerifier.verify(token);

			var isExpired = decodedJwt.getExpiresAt().before(new Date(System.currentTimeMillis()));
			if (isExpired)
				throw new JwtServiceException();

			var jwtTokenData = new JwtTokenData();
			jwtTokenData.setEmail(decodedJwt.getClaim("email").asString());
			jwtTokenData.setId(decodedJwt.getClaim("id").asInt());
			jwtTokenData.setRole(decodedJwt.getClaim("role").asString());
			jwtTokenData.setName(decodedJwt.getClaim("name").asString());
			jwtTokenData.setSurname(decodedJwt.getClaim("surname").asString());

			return Optional.of(jwtTokenData);
		} catch (IllegalArgumentException |
				JWTVerificationException e) {
			throw new JwtServiceException();
		}
	}

	public boolean hasAnAdminUser(String authorization) {
		var jwtToken = authorization.substring(7);
		Optional<JwtTokenData> jwtTokenData;
		try {
			jwtTokenData = validateJwt(jwtToken);
		}
		catch (JwtServiceException e) {
			return false;
		}

		if (jwtTokenData.isEmpty())
			return false;

		return jwtTokenData.get().getRole().equals("Admin");
	}

	public String getEmail(String authorization) {
		var jwtToken = authorization.substring(7);
		var jwtTokenData = validateJwt(jwtToken);

		return jwtTokenData.get().getEmail();
	}
}
