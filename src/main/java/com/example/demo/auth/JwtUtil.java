package com.example.demo.auth;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
  private final SecretKey key;

  public JwtUtil(SecretKey key) {
      this.key = key;
  }
  
  public String generateToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
        .signWith(key)
        .compact();
  }
  
  public String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }
  
  public boolean isTokenValid(String token) {
    try {
      extractUsername(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
