package com.example.springSecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component
@NoArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils = new JwtUtils();

//    @Autowired
//    public void setJwtUtils(){
//        System.out.println("inside set jwt utils");
//        this.jwtUtils = new JwtUtils();
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String tokenPrefix = "Bearer ";
        System.out.println("inside filter");
        if(authorizationHeader == null || !authorizationHeader.startsWith(tokenPrefix)){
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authorizationHeader.substring(tokenPrefix.length());
        final String username = new JwtUtils().getEmail(token);
        System.out.println(username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = new User(username, "");
            if(jwtUtils.isTokenValid(token, user)){
                System.out.println("inside valid ");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
