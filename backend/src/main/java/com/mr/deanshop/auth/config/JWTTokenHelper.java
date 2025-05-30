package com.mr.deanshop.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenHelper {

    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey;
    @Value("${jwt.auth.expires_in}")
    private int expireIn;

    public String generateToken(String email) {
        return Jwts.builder()
                .issuer(appName)
                .subject(email)
                .issuedAt(new Date())
                .signWith(getSignKey())
                .expiration(generateExpirationDate())
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expireIn * 1000L);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            return authToken.substring(7);
        }
        return authToken;
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        String email = getEmailFromToken(authToken);

        return email != null && email.equals(userDetails.getUsername()) && !isTokenExpired(authToken);
    }

    public String getEmailFromToken(String authToken) {
        String email;
        try{
            Claims claims = getAllClaimsFromToken(authToken);
            email = claims.getSubject();
        }catch (Exception e){
            email = null;
        }
        return email;
    }

    public boolean isTokenExpired(String token) {
        Date expireDate = this.getExpirationDate(token);
        return expireDate.before(new Date());
    }

    public Date getExpirationDate(String token) {
        Date expirationDate;
        try{
            Claims claims = this.getAllClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        }catch(Exception e){
            expirationDate = null;
        }
        return expirationDate;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }
}
