package com.example.demo.auth;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    SecretKey jwtSigningKey() {
        byte[] decodedKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
