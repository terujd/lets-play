package mijan.letsplay.services;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
    public static final String SECRET = generateRandomToken();

    /**
     * Generates a random token.
     *
     * @implSpec This method generates a random token using the SecureRandom class.
     * @implNote To generate a random token is considered as a good practice.
     *
     * @return the randomly generated token
     */
    private static String generateRandomToken() {
        SecureRandom random = new SecureRandom();// this is a random number generator
        byte[] bytes = new byte[32];// this is a byte array
        random.nextBytes(bytes);// this is a method of SecureRandom class
        System.out.println("Random token: " + Base64.getEncoder().encodeToString(bytes)); // this is a method of Base64 class
        return Base64.getEncoder().encodeToString(bytes);// this is a method of Base64 class
    }

    /**
     * Extracts the username from the given token.
     *
     * @param token the token from which to extract the username
     * @return the extracted username as a string
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);// here we extract the username from the token
    }

    /**
     * Extracts the expiration date from a token.
     *
     * @param token the token from which to extract the expiration date
     * @return the expiration date extracted from the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);// here we extract the expiration date from the token
    }

    /**
     * Extracts a claim from a token using the provided claims resolver function.
     *
     * @param token          the token from which to extract the claim
     * @param claimsResolver the function used to extract the claim from the token's
     *                       claims
     * @return the extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);// here we extract all the claims from the token
        return claimsResolver.apply(claims);// here we apply the claimsResolver function to the claims
    }

    /**
     * Extracts all claims from a given token.
     *
     * @param token the token from which to extract the claims
     * @return the extracted claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()// here we build the parser
                .setSigningKey(getSignKey())// here we set the sign key
                .build()// here we build the parser
                .parseClaimsJws(token)// here we parse the claims
                .getBody();// here we get the body of the claims
    }

    /**
     * Checks if a given token is expired.
     *
     * @param token the token to be checked
     * @return true if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());// here we check if the token is expired
    }

    /**
     * Validates a token by comparing the username extracted from the token
     * with the username from the user details and checking if the token
     * has expired.
     *
     * @param token       the token to validate
     * @param userDetails the user details object containing the username
     * @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);// here we extract the username from the token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));// here we check if the token is valid
    }

    /**
     * Generates a token for the given user name.
     * 
     * @implNote each token is consist of (Header[used algo], Payload[data], Verify
     *           Signature[Secret code]) which is known as claims in jwt.
     * 
     * @implSpec This method generates a token for the given user name using the JWT
     *           (JSON Web Token) standard.
     *
     * @param userName the user name for which to generate the token
     * @return the generated token
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();// here we create a map of claims
        return createToken(claims, userName);// here we create the token
    }

    /**
     * Creates a JWT token with the provided claims and username.
     *
     * @param claims   a map containing the claims to be included in the token
     * @param userName the username to be included in the token
     * @return the generated JWT token
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()// here we build the token
                .setClaims(claims)// here we set the claims
                .setSubject(userName)// here we set the subject
                .setIssuedAt(new Date(System.currentTimeMillis()))// here we set the issued date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))// here we set the expiration date
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact(); // add the signature key with my own SECRET
                                                                             // (so here we add 1'st and 3'ed jwt
                                                                             // components).
    }

    /**
     * Generates a sign key for the Java function.
     *
     * @return The generated sign key.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);// here we decode the secret key
        return Keys.hmacShaKeyFor(keyBytes);// here we return the sign key
    }
}