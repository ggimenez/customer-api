package com.gabri.coding.customerapi.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private final int JWT_HOURS_EXPIRATION = 24;
    private final int JWT_MILLISECOND_EXPIRATION = JWT_HOURS_EXPIRATION * 60 * 60 * 1000;

    private final Algorithm hmac512;
    private final JWTVerifier verifier;

    public JwtTokenService(@Value("${jwt.secret}") final String secret) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateToken(final UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_MILLISECOND_EXPIRATION))
                .sign(this.hmac512);
    }

    public String validateTokenAndGetUsername(final String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            logger.warn("token invalid: {}", verificationEx.getMessage());
            return null;
        }
    }

}