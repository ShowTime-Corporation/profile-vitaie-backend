package showtime_corp.profile_vitaile.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration for the application.
 *
 * <p>
 * This class enables Cross-Origin Resource Sharing (CORS) for the API,
 * allowing the frontend (e.g., Angular running on <b>localhost:4200</b>) to
 * communicate securely with the backend.
 * </p>
 *
 * <p>
 * CORS is required when the frontend and backend run on different domains or ports.
 * Without this configuration, the browser blocks HTTP requests for security reasons.
 * </p>
 *
 * <p><b>Key CORS settings enabled:</b></p>
 * <ul>
 *     <li><b>allowedOrigins:</b> Only allows requests from the Angular app</li>
 *     <li><b>allowedMethods:</b> Controls which HTTP methods are accepted</li>
 *     <li><b>allowedHeaders:</b> Allows any custom header sent by the frontend</li>
 *     <li><b>allowCredentials:</b> Enables cookies, JWT in headers, etc.</li>
 * </ul>
 */
@Configuration
public class CorsConfig {

    /**
     * Registers a custom CORS configuration for the entire application.
     *
     * @return A {@link WebMvcConfigurer} instance that applies global CORS rules.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            /**
             * Configures allowed origins, methods, headers, and credential behavior.
             *
             * @param registry The CORS registry where mappings are added.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all API endpoints
                        .allowedOrigins("http://localhost:4200") // Allow Angular local dev client
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow any header
                        .allowCredentials(true); // Allow cookies / Authorization headers
            }
        };
    }
}
