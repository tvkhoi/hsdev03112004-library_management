package com.example.jwt_bai3.services;

import com.example.jwt_bai3.dto.AuthRequest;
import com.example.jwt_bai3.dto.ProfileResponse;
import com.example.jwt_bai3.entities.Reader;
import com.example.jwt_bai3.repositories.ReaderRepository;
import com.example.jwt_bai3.securities.JwtUtil;
import com.example.jwt_bai3.services.implement.ReaderServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ReaderServices implements ReaderServicesImp {
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    JwtUtil jwtUltil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean register(Reader reader) {
        if(!readerRepository.findByEmail(reader.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Email already exists.");
        }
        String role = reader.getRole();
        if(role == null || !(role.equals("User") || role.equals("Admin"))) {
            throw new IllegalArgumentException("Invalid role.");
        }
        // Password encryption
        reader.setPassword(passwordEncoder.encode(reader.getPassword()));
        return readerRepository.save(reader) != null ? true : false;
    }

    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUltil.generateToken(authRequest.getEmail());
    }

    @Override
    public ProfileResponse getProfile(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException("No valid token provided.");
        }
        String token = authHeader.substring(7); //Eliminate "Bearer " to get token
        String email = jwtUltil.getEmailFromJwtToken(token);
        Reader reader = readerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return new ProfileResponse(reader.getName(), reader.getEmail());
    }


}
