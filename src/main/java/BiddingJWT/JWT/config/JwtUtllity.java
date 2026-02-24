package BiddingJWT.JWT.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtllity {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.token.validity}")
    private Long expiration;

    public SecretKey getKey() {
        byte[] byteArray = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(byteArray);
    }

    public String generateToken(String username, String role, Integer id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return getAllClaims(token).getSubject();
    }

    public Date getExpiration(String token) {
        return getAllClaims(token).getExpiration();
    }

    public Boolean validateToken(String token) {
        return !getExpiration(token).before(new Date());
    }
}