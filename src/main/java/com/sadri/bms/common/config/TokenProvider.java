package com.sadri.bms.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class TokenProvider {

    private final String secretKey = "bms-secret-key";
    private final String issuer = "bms-issuer";

    public String getToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withClaim("accountPassword", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String verifyToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(issuer)
                .build().verify(token).getClaim("accountPassword").asString();
    }

}
