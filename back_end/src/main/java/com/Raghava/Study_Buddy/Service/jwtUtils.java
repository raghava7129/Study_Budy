package com.Raghava.Study_Buddy.Service;

import com.Raghava.Study_Buddy.Models.loginUserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

@Component
public class jwtUtils {

    private String secretKey;
    private long tokenExpiration;

    public jwtUtils() {
        try{
            Resource resource = new ClassPathResource("/application.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            secretKey = properties.getProperty("secretKey");
            tokenExpiration = Long.parseLong(properties.getProperty("app.tokenExpiration"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String generateToken(String username,String password ,String email) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        Key signingKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .claim("user_email", email)
                .claim("PASSWORD", password)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public loginUserRequest decodeJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        loginUserRequest loginUserRequest = new loginUserRequest();
        loginUserRequest.setUsername(claims.getSubject());
        loginUserRequest.setPassword((String) claims.get("PASSWORD"));
        loginUserRequest.setEmail((String) claims.get("user_email"));
        return loginUserRequest;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
    };
        return false;
    }
}

