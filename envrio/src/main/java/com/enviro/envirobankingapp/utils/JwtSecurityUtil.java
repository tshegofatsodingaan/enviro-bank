package com.enviro.envirobankingapp.utils;

import com.enviro.envirobankingapp.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
@Service
public class JwtSecurityUtil {

    @Value("${eb.jwtSecret}")
    private String jwtSecret;

    @Value("${eb.jwtExpirationDate}")
    private String jwtExpirationDate;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            String email = claims.get("email", String.class);
            if(email != null){
                return email;
            }else{
                System.out.println("Your token is missing the email");
                return null;
            }

        }catch (Exception e){
            System.out.println("Token parsing failed: " + e.getMessage());
            return null;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, Set<UserRole> roles, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", roles);
        return createToken(claims, username);
    }

//    public String generateToken(Authentication authentication){
//        String username = authentication.getName(); // can contain name or email
//
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
//
//        String token = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS256, key())
//                .compact();
//        return token;
//    }

//    public Key key(){
//        return Keys.hmacShaKeyFor(
//                Decoders.BASE64.decode(jwtSecret)
//        );
//    }

//    public String getUsername(String token){
//        Claims claims = Jwts.parser()
//                .setSigningKey(key())
//                .parseClaimsJws(token)
//                .getBody();
//        String username = claims.getSubject();
//        return username;
//    }

//    public Boolean validateToken(String token){
//        Jwts.parser()
//                .setSigningKey(key())
//                .parse(token);
//        return true;
//    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // ONE HOUR
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
