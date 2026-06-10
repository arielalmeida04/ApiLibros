package com.company.books.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private static final String JWT_SECRET_KEY = "jwtsecretkey12345678901234567890";
 

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 1; // 1 hora

    // Convierte el String secreto en un SecretKey que exige la nueva API
    private SecretKey getSigningKey() {
        byte[] keyBytes = JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()                     // antes: Jwts.parser()
                .verifyWith(getSigningKey())      // antes: .setSigningKey(JWT_SECRET_KEY)
                .build()                         // NUEVO: necesario en 0.12.x
                .parseSignedClaims(token)        // antes: .parseClaimsJws(token)
                .getPayload();                   // antes: .getBody()
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
        claims.put("rol", rol);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .claims(claims)                                              // antes: .setClaims(claims)
                .subject(subject)                                            // antes: .setSubject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))              // antes: .setIssuedAt(...)
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // antes: .setExpiration(...)
                .signWith(getSigningKey())                                   // antes: .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}