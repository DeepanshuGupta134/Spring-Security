package com.example.springSecurity.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtUtils {

    public JwtUtils(){
        System.out.println("inside jwt utils");
    }

    //private final SecretKey secret = secretKeyFor(SignatureAlgorithm.HS256);
    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private long accessTokenValidity = 60*60*1000;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public String createJwt(User user){
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        System.out.println("secret" + secretKey);

        return token;
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token){
        return getClaims(token).getSubject();
    }

    public String getFirstName(String token){
        return (String) getClaims(token).get("firstName");
    }

    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().after(new Date());
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = getEmail(token);
//        return  !isTokenExpired(token);
        return username.equals(userDetails.getUsername());
        //return true ;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
