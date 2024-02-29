package com.bos.bos.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Class:1
@Component
public class JwtService {

    //Method:1
    @Value("${jwt.key}")
    private String SECRET;

    @Value("${jwt.expirationAccessToken}")
    public long expirationAccessToken;

    //Method:5
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Method:6
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Method:7
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Method:8
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return(username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //Method:9
    /*private String createToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                *//*.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiration)))*//*
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }*/

    private String createToken(Map<String, Object> claims, String userName, long expirationAccessToken) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationAccessToken))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Method:10
    public String GenerateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName, expirationAccessToken);
    }

    //Method:2
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                //KEY type Data
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Method:4
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Method:3
    private Key getSignKey() {
        if (SECRET == null || SECRET.isEmpty()) {
            throw new IllegalArgumentException("SECRET is null or empty. Please set a valid secret key.");
        }
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
