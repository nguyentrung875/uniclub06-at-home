package com.cybersoft.uniclub06.service.imp;

import com.cybersoft.uniclub06.dto.AuthorityDTO;
import com.cybersoft.uniclub06.dto.RoleDTO;
import com.cybersoft.uniclub06.entity.UserEntity;
import com.cybersoft.uniclub06.repository.UserRepository;
import com.cybersoft.uniclub06.request.AuthenRequest;
import com.cybersoft.uniclub06.service.AuthenService;
import com.cybersoft.uniclub06.utils.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String login(AuthenRequest request) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authenToken
                = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authen = authenticationManager.authenticate(authenToken);
        List<GrantedAuthority> listRole= (List<GrantedAuthority>) authen.getAuthorities();

//        List<String> roles = new ArrayList<>();
//        listRole.forEach(role -> {
//            roles.add(role.getAuthority());
//        });
//
        var roles = listRole.stream().map(role -> role.getAuthority()).toList();

        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setUsername(request.email());
        authorityDTO.setRoles(roles);

        return jwtHelper.generateJwtToken(authorityDTO);
    }


}
