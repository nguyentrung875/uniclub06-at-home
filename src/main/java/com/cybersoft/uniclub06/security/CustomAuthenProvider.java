package com.cybersoft.uniclub06.security;

import com.cybersoft.uniclub06.entity.RoleEntity;
import com.cybersoft.uniclub06.entity.UserEntity;
import com.cybersoft.uniclub06.repository.UserRepository;
import com.cybersoft.uniclub06.request.AuthenRequest;
import com.cybersoft.uniclub06.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity userEntity = userRepository.findUserEntitiesByEmail(username);

        if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())){
            List<GrantedAuthority> authorityList = new ArrayList<>();

            RoleEntity role = userEntity.getRole();
            authorityList.add(new SimpleGrantedAuthority(role.getName()));

            return new UsernamePasswordAuthenticationToken(username, "", authorityList);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
