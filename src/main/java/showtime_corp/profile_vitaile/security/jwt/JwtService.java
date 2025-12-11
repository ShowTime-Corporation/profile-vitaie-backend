package showtime_corp.profile_vitaile.security.jwt;

import io.jsonwebtoken.Claims; // Importación para manejar los claims
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders; // Nuevo import
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; // Nuevo import
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function; // Nuevo import

@Service
public class JwtService {
    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    // --- Métodos de Ayuda ---

    private Key getSigningKey() {
        // Usa Decoders para decodificar la clave de forma segura
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser() // Uso de builder moderno
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // --- Métodos Requeridos por el Filtro ---

    // 1. Extraer Email (Subject) del Token
    public String getEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Generar Token (Mantienes tu lógica, pero usa builder moderno)
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); // O simplemente .signWith(getSigningKey(), SignatureAlgorithm.HS256)                .compact();
    }

    // --- Sobrecarga para login: generar token desde el User ---
    public String generateToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .claim("role", user.getSub())
                .setSubject(user.getEmail()) // Still set subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // 3. Chequear Expiración
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 4. Validación Final (Usado por JwtAuthenticationFilter)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getEmailFromToken(token);
        // Debe coincidir el email Y no debe estar expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
