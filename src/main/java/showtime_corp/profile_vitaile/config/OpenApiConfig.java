package showtime_corp.profile_vitaile.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Global OpenAPI configuration for Swagger documentation.
 * <p>
 * This configuration registers:
 * <ul>
 *     <li>API metadata (title, description, version)</li>
 *     <li>JWT Bearer authentication schema</li>
 *     <li>Global security requirement applied to every endpoint except those annotated manually</li>
 *     <li>Server definitions</li>
 * </ul>
 *
 * <p>To authenticate in Swagger UI:</p>
 * <ol>
 *     <li>Obtain a token via <code>/auth/login</code></li>
 *     <li>Click the <b>Authorize</b> button</li>
 *     <li>Insert: <code>Bearer &lt;token&gt;</code></li>
 * </ol>
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Profile Vitaile API",
                version = "1.0",
                description = "API documentation for the user profile and authentication system.",
                contact = @Contact(
                        name = "Showtime Corp",
                        email = "support@showtimecorp.com",
                        url = "https://showtimecorp.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server"),
                @Server(url = "https://api.showtimecorp.com", description = "Production server")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Insert your JWT token: **Bearer &lt;token&gt;**"
)
public class OpenApiConfig {
    // Empty class — configuration is done entirely via annotations
}
