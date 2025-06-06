package com.example.jwt_bai3.services;

import com.example.jwt_bai3.entities.Reader;
import com.example.jwt_bai3.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeltailService implements UserDetailsService {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Reader reader = readerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        // GrantedAuthority role mapping
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + reader.getRole()));
        return new User(reader.getEmail(), reader.getPassword(), authorities);
    }
}
