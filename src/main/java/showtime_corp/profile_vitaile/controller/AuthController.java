package showtime_corp.profile_vitaile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.AuthRequest;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.service.AuthService;

/**
 * REST controller that handles authentication operations such as
 * user registration and login.
 *
 * <p>This controller exposes endpoints for user onboarding and issuing
 * authentication tokens. It delegates all business logic to {@link AuthService}.</p>
 *
 * @author Santiago Toro y Andres Niebles
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user in the system.
     *
     * @param request the registration payload containing user information
     * @return a confirmation message upon successful registration
     */
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email already in use",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.registerNewUser(request);
        return ResponseEntity.ok("User registered!");
    }

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param request the login payload containing email and password
     * @return an authentication response containing the access token
     */
    @Operation(
            summary = "Authenticate user",
            description = "Validates user credentials and returns a JWT token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authenticated successfully",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
