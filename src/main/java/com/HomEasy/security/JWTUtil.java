package com.HomEasy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("FDSAfvfdvfduj")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException{
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("Event Scheduler")
                .sign(Algorithm.HMAC256(secret));
    }
}
