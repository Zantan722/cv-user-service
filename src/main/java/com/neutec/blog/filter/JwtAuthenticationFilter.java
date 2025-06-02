package com.neutec.blog.filter;


import com.neutec.blog.db.dao.UserRepository;
import com.neutec.blog.db.entity.User;
import com.neutec.blog.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.neutec.blog.constant.HttpConstant.*;


@Component
@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header != null && header.startsWith(HEADER_AUTHORIZATION_PREFIX)) {
            String token = header.substring(HEADER_AUTHORIZATION_PREFIX.length());

            if (jwtService.validateToken(token)) {
                String email = jwtService.getEmailFromToken(token);
                User user = userRepository.findByEmail(email);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name()))
                        );
                    request.setAttribute(ATTRIBUTE_EMAIL, user.getEmail());
                    request.setAttribute(ATTRIBUTE_USER_ID, user.getId());
                    request.setAttribute(ATTRIBUTE_USER_ROLE, user.getRole().name());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}