package com.sam.webapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sam.webapi.security.PemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Service
public class JwtService {

	private String issuer = "Soccer Activities Management";
	private long duration = 20 * 60 * 60 * 1000; // 20 hours

	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	private Algorithm algorithm;

	public JwtService(@Value("${jwt.publicKeyPath}") String publicKeyPath,
					  @Value("${jwt.privateKeyPath}") String privateKeyPath) throws JWTCreationException {
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

	public String createJwt(String email, String role, String name, String surname) {
		var currentTime = System.currentTimeMillis();

		return JWT.create()
				.withIssuer(issuer)
				.withExpiresAt(new Date(currentTime + duration))
				.withIssuedAt(new Date(currentTime))
				.withClaim("email", email)
				.withClaim("role", role)
				.withClaim("name", name)
				.withClaim("surname", surname)
				.sign(algorithm);
	}
}
