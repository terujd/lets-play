package mijan.letsplay.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Create a token and set its claims
    import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret; // You can store the secret in application.properties or as an environment variable

    @Value("${jwt.expirationInMs}")
    private Long expirationInMs; // You can configure the token expiration time

    // Generate a JWT token given a username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        byte[] signingKeyBytes = DatatypeConverter.parseBase64Binary(secret);
        SecretKey signingKey = new SecretKeySpec(signingKeyBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
                .signWith(signingKey)
                .compact();
    }

    // Extract the username from a token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a claim from a token using the provided claim resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from a token
    private Claims extractAllClaims(String token) {
        byte[] signingKeyBytes = DatatypeConverter.parseBase64Binary(secret);
        SecretKey signingKey = new SecretKeySpec(signingKeyBytes, SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }

    // Check if a token is expired
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract the token expiration date from a token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        return false;
    }
}


