package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${spring.app.JwtSecret}")
    private String secret ;
//    @Value("${spring.app.JwtExpirationMs}")
//    private String JwtExpirationMs;
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isAccessToken(String token) {
        Claims claims = extractAllClaims(token);
        boolean flag= "access".equals(claims.get("type"));
        return flag;
    }

    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        boolean flag= "refresh".equals(claims.get("type"));
        return flag;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String authToken)
    {
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            if (isTokenExpired(authToken))
            {
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return createToken(claims, email, 1000 * 60 * 1);
    }

    public String generateRefreshToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return createToken(claims, email, 1000 * 60 * 60 * 24 * 7);
    }
    private String createToken(Map<String, Object> claims, String subject, Integer expire) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +expire ))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

        public String getJwFromHeader(HttpServletRequest httpRequest)
    {
        String bearerToken=httpRequest.getHeader("Authorization");
        if (bearerToken!=null&&bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }

}

