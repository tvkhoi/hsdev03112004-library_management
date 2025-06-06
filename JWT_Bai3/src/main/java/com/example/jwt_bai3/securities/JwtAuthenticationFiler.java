package com.example.jwt_bai3.securities;

import com.example.jwt_bai3.services.UserDeltailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFiler extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUltil;
    @Autowired
    private UserDeltailService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");

        if(authToken != null && authToken.startsWith("Bearer ")) {
            String jwt = authToken.substring(7);

            try {
                if(jwtUltil.validateJwtToken(jwt)) {
                    String email = jwtUltil.getEmailFromJwtToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new org.springframework.security.core.AuthenticationException("Invalid JWT token") {};
                }
            } catch (Exception ex) {
                throw new org.springframework.security.core.AuthenticationException("JWT error: " + ex.getMessage()) {};
            }
        }
        filterChain.doFilter(request, response);
    }
}
