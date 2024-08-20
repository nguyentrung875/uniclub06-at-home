package com.cybersoft.uniclub06.security;

import com.cybersoft.uniclub06.dto.AuthorityDTO;
import com.cybersoft.uniclub06.dto.RoleDTO;
import com.cybersoft.uniclub06.utils.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.getJwtFromRequest(request);

        if (token != null) {
            AuthorityDTO authorityDTO = jwtHelper.decodeToken(token);

            List<SimpleGrantedAuthority> authorityList =
                    authorityDTO.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).toList();

            UsernamePasswordAuthenticationToken authenticationToken =
                     new UsernamePasswordAuthenticationToken(authorityDTO.getUsername(),"", authorityList);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            System.out.println("token substring: " + bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}
