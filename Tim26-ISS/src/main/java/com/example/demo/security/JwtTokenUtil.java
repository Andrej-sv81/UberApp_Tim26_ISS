package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;

    // vrati kor ime iz jwt tokena
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    // vrati datum isteka za jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // za vracanje bilo koje info od tokena potreban je kljuc
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //proveri da li je isteko token
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generisi token za korisnika
    public String generateToken(String username, String role, Integer id){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("id",id);
        return doGenerateToken(claims, username);
    }

    // kreiranje tokena
    // 1. definisanje tokena expiration, subject, id...
    // 2. dodavanje kljuca
    // kompakcija tokena u url-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateRefreshToken(String email, String role, Integer id) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("id", id);
        return doGenerateRefreshToken(claims, email);
    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String email) {
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_REFRESH_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
}
