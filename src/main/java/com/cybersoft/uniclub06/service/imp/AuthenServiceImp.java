package com.cybersoft.uniclub06.service.imp;

import com.cybersoft.uniclub06.entity.UserEntity;
import com.cybersoft.uniclub06.repository.UserRepository;
import com.cybersoft.uniclub06.request.AuthenRequest;
import com.cybersoft.uniclub06.service.AuthenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenServiceImp implements AuthenService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final long EXPIRATION_TIME = 864_000_000; // 10 days
    private final String SECRET_KEY_STRING = "b3781410556dfa65441d63fd09caa10c0e813c501fc88e7f2ca5596d13c9a022";

    @Override
    public String checkLogin(AuthenRequest request) {
        String token = "";
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(request.email());

        if (userEntity != null && passwordEncoder.matches(request.password(), userEntity.getPassword())){
            token = this.generateJwtToken(userEntity);
        }

        return token;
    }

    private String generateJwtToken(UserEntity userEntity) {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY_STRING);
        var secretKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject(userEntity.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}
