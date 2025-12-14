package showtime_corp.profile_vitaile.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import showtime_corp.profile_vitaile.security.jwt.JwtAuthenticationFilter;

import java.util.List;

/**
 * Configuration class responsible for defining Spring Security settings.
 *
 * <p>This includes:</p>
 * <ul>
 *     <li>Endpoint authorization rules</li>
 *     <li>JWT authentication filter registration</li>
 *     <li>Disabling CSRF for stateless APIs</li>
 *     <li>Password encoder configuration</li>
 *     <li>AuthenticationManager exposure</li>
 * </ul>
 *
 * <p>
 * The application runs in a fully stateless mode using JWT tokens,
 * meaning no HTTP session is used to store authentication state.
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the HTTP security filter chain used by Spring Security.
     *
     * <p><b>Main configurations:</b></p>
     * <ul>
     *     <li>Disables CSRF because the API uses JWT (stateless)</li>
     *     <li>Allows public access to authentication and Swagger endpoints</li>
     *     <li>Requires authentication for all other endpoints</li>
     *     <li>Sets session strategy to STATELESS</li>
     *     <li>Registers the custom {@link JwtAuthenticationFilter} before the standard login filter</li>
     * </ul>
     *
     * @param http The {@link HttpSecurity} instance configured by Spring.
     * @return A fully configured {@link SecurityFilterChain}.
     * @throws Exception If any configuration step fails.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public authentication endpoints
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/register", "/auth/login").permitAll()

                        // Swagger / OpenAPI endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/prometheus"
                        ).permitAll()

                        .requestMatchers("/actuator/**").hasRole("ADMIN")

                        // All other routes require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Adds the JWT filter before Spring's login filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    /**
     * Configures the Cross-Origin Resource Sharing (CORS) settings for the application.
     *
     * <p>This bean defines the CORS policy, which is crucial for allowing frontend applications
     * hosted on different origins (e.g., http://localhost:4200) to interact with the API.</p>
     *
     * <p><b>Current configuration:</b></p>
     * <ul>
     *     <li><b>Allowed Origins:</b> Only 'http://localhost:4200' is permitted.</li>
     *     <li><b>Allowed Methods:</b> GET, POST, PUT, DELETE, OPTIONS.</li>
     *     <li><b>Allowed Headers:</b> All headers are permitted.</li>
     *     <li><b>Allow Credentials:</b> Set to true to support cookies and authentication headers.</li>
     * </ul>
     *
     * @return A {@link CorsConfigurationSource} instance with the defined settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Exposes the {@link AuthenticationManager} as a Spring Bean.
     *
     * <p>
     * This allows authentication logic (e.g., during login)
     * to be performed manually using Spring Security mechanisms.
     * </p>
     *
     * @param config The automatically configured {@link AuthenticationConfiguration}.
     * @return The authentication manager instance.
     * @throws Exception If initialization fails.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides the password encoder used to hash and verify user passwords.
     *
     * <p>
     * Uses {@link BCryptPasswordEncoder} because:
     * </p>
     * <ul>
     *     <li>It is secure and recommended by Spring</li>
     *     <li>It automatically handles salting</li>
     *     <li>It is resistant to brute-force attacks</li>
     * </ul>
     *
     * @return A BCrypt-based {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
