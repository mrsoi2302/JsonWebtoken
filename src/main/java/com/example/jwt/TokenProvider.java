package com.example.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private Key getSigninKey(SignatureAlgorithm alg) {
        switch (alg.getValue()) {
            case "HS256" -> {
                return Keys.secretKeyFor(SignatureAlgorithm.HS256);
            }
            case "HS384" -> {
                return Keys.secretKeyFor(SignatureAlgorithm.HS384);
            }
            case "HS512" -> {
                return Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
            case "RS256" -> {
                return Keys.keyPairFor(SignatureAlgorithm.RS256).getPrivate();
            }
            case "RS384" -> {
                return Keys.keyPairFor(SignatureAlgorithm.RS384).getPrivate();
            }
            case "RS512" -> {
                return Keys.keyPairFor(SignatureAlgorithm.RS512).getPrivate();
            }
            default -> throw new RuntimeException("Invalid algorithm");
        }
    }

    public String tokenGenerator(SignatureAlgorithm alg, String username) {
        com.example.jwt.Key.Value = getSigninKey(alg);
        var key = com.example.jwt.Key.Value;
        log.warn(key.toString());
        log.warn(key.getAlgorithm() + " " + key.getFormat() + " " + Arrays.toString(key.getEncoded()));
        long EXPIRATED = 1000 * 60 * 60 * 24;
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", alg.getValue())
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATED))
                .signWith(key, alg)
                .compact();
    }

    public String getUser(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(com.example.jwt.Key.Value)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(com.example.jwt.Key.Value)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException){
                log.info("Token expired");
            }
            throw e;
        }
    }

}
