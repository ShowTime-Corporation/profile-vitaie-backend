package showtime_corp.profile_vitaile.unit.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.security.jwt.JwtService;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;
    private User user;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // 🔐 Secret BASE64 válido (mínimo 256 bits)
        String secret = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";
        Long expiration = 1000 * 60 * 60L; // 1 hora

        ReflectionTestUtils.setField(jwtService, "secretKey", secret);
        ReflectionTestUtils.setField(jwtService, "expiration", expiration);

        user = new User();
        user.setId(1L);
        user.setFirstName("Unit");
        user.setLastName("Test");
        user.setEmail("unit@test.com");
        user.setPassword("Password123");
        user.setActive(true);
        user.setSub(User.UserSub.FREE);
    }

    @Test
    @DisplayName("Should generate JWT token for user")
    void shouldGenerateToken() {
        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("Should extract email from token")
    void shouldExtractEmailFromToken() {
        String token = jwtService.generateToken(user);

        String email = jwtService.getEmailFromToken(token);

        assertThat(email).isEqualTo("unit@test.com");
    }

    @Test
    @DisplayName("Should extract userId from token")
    void shouldExtractUserIdFromToken() {
        String token = jwtService.generateToken(user);

        Integer userId = jwtService.getUserIdFromToken(token);

        assertThat(userId).isEqualTo(1);
    }

    @Test
    @DisplayName("Should validate token successfully")
    void shouldValidateToken() {
        String token = jwtService.generateToken(user);

        boolean valid = jwtService.isTokenValid(token, user);

        assertThat(valid).isTrue();
    }

    @Test
    @DisplayName("Should invalidate token when user email does not match")
    void shouldInvalidateTokenForDifferentUser() {
        String token = jwtService.generateToken(user);

        User otherUser = new User();
        otherUser.setEmail("other@test.com");

        boolean valid = jwtService.isTokenValid(token, otherUser);

        assertThat(valid).isFalse();
    }
}
