package com.example.jwt_bai3.securities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private int expirationMs;

    // create token from username
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationMs))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // get email from token
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Check if token is valid
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException signatureException) {
            System.err.println("Invalid JWT signature: " + signatureException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            System.err.println("Expired JWT token: " + expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Unsupported JWT token: " + unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            System.err.println("JWT claims string is empty: " + illegalArgumentException.getMessage());
        }
        return false;
    }
}
