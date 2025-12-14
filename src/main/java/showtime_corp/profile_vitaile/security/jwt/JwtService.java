package showtime_corp.profile_vitaile.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Service responsible for generating, parsing and validating JWT tokens.
 *
 * <p>This service supports:
 * <ul>
 *     <li>Extracting claims from a token.</li>
 *     <li>Creating tokens for login.</li>
 *     <li>Validating token expiration and signature.</li>
 *     <li>Embedding custom claims for user identity.</li>
 * </ul>
 *
 * <p>The secret key and expiration time are loaded from application properties.
 */
@Service
public class JwtService {

    /** Secret key used for signing tokens (Base64 encoded). */
    @Value("${security.jwt.secret}")
    private String secretKey;

    /** Expiration time in milliseconds. */
    @Value("${security.jwt.expiration}")
    private Long expiration;

    // ======================
    // Internal Helper Methods
    // ======================

    /**
     * Returns the signing key used to sign and validate JWT tokens.
     * The key is decoded from Base64.
     *
     * @return HMAC signature key.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token.
     * @return The parsed Claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts a specific claim from a token using a resolver function.
     *
     * @param token The JWT token.
     * @param claimsResolver Function that retrieves a specific claim.
     * @param <T> Type of the expected claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date of the token.
     *
     * @param token JWT token.
     * @return Expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ======================
    // Public API
    // ======================

    /**
     * Retrieves the email (subject) contained in the JWT token.
     *
     * @param token The token.
     * @return The email of the user.
     */
    public String getEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a JWT token containing only the email (simple token).
     *
     * @param email Email to set as subject.
     * @return Generated JWT.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JWT token embedding user-specific claims.
     *
     * @param user User whose data will be added to the token.
     * @return A JWT with custom claims.
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("role", user.getSub())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the token has expired.
     *
     * @param token JWT token.
     * @return true if expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the token:
     * <ul>
     *     <li>Checks if the subject matches the user email.</li>
     *     <li>Checks if the token is not expired.</li>
     * </ul>
     *
     * @param token The JWT token.
     * @param userDetails Spring Security user details.
     * @return true if valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getEmailFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    /**
     * Retrieves the user ID from the JWT token.
     *
     * @param token JWT token.
     * @return User ID.
     */
    public Integer getUserIdFromToken(String token) {
        return extractClaim(token, claims -> claims.get("id", Integer.class));
    }
}
