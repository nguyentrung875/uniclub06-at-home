package com.cybersoft.uniclub06.service.imp;

import com.cybersoft.uniclub06.entity.UserEntity;
import com.cybersoft.uniclub06.repository.UserRepository;
import com.cybersoft.uniclub06.request.AuthenRequest;
import com.cybersoft.uniclub06.service.AuthenService;
import com.cybersoft.uniclub06.utils.JwtHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenServiceImp implements AuthenService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;


    @Override
    public boolean checkLogin(AuthenRequest request) {
        boolean isSuccess = false;
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(request.email());

        if (userEntity != null && passwordEncoder.matches(request.password(), userEntity.getPassword())){
            isSuccess = true;
        }

        return isSuccess;
    }

    @Override
    public String login(AuthenRequest request){
        UsernamePasswordAuthenticationToken authenToken
                = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        authenticationManager.authenticate(authenToken);

        return jwtHelper.generateJwtToken(request.email());
    }


}
