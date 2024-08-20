package com.cybersoft.uniclub06.utils;

import com.cybersoft.uniclub06.dto.AuthorityDTO;
import com.cybersoft.uniclub06.dto.RoleDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtHelper {
    @Value("${jwt.key}")
    private String secretKeyString;

    private final long EXPIRATION_TIME = 24*60*60*1000;

    public String generateJwtToken(AuthorityDTO authorityDTO) {
        var secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));

        return Jwts.builder()
                .subject(authorityDTO.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("roles" , authorityDTO.getRoles())
                .signWith(secretKey)
                .compact();
    }

    public AuthorityDTO decodeToken(String token) throws JsonProcessingException {
        AuthorityDTO authorityDTO = new AuthorityDTO();

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyString));
            Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

            var roles = claims.getPayload().get("roles", List.class);

            authorityDTO.setUsername(claims.getPayload().getSubject());
            authorityDTO.setRoles(roles);

        return authorityDTO;
    }

}
