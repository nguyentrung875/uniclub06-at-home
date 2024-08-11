package com.cybersoft.uniclub06.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.key}")
    private String secretKeyString;

    private final long EXPIRATION_TIME = 24*60*60*1000;

    public String generateJwtToken(String data) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        var secretKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject(data)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String decodeToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyString));

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

}
