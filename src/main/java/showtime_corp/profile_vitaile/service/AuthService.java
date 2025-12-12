package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.BadRequestException;
import showtime_corp.profile_vitaile.exception.ConflictException;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.security.jwt.JwtService;

/**
 * Service responsible for handling authentication and user registration logic.
 * <p>
 * This service manages:
 * <ul>
 *     <li>User registration (validations, password encryption)</li>
 *     <li>User login and credential verification</li>
 *     <li>JWT token generation</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registers a new user in the system.
     *
     * <p><b>Validations performed:</b></p>
     * <ul>
     *     <li>Ensures email is not already registered</li>
     *     <li>Encrypts and stores the user password</li>
     * </ul>
     *
     * @param request The registration data provided by the client.
     * @return The newly created {@link User} entity.
     *
     * @throws ConflictException If the email is already in use.
     */
    public User registerNewUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Encrypt password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        // Default user properties
        user.setActive(true);
        user.setSub(User.UserSub.FREE);
        user.setResumen(null);
        user.setRoadMap(null);
        user.setEmployability(null);

        return userRepository.save(user);
    }

    /**
     * Authenticates a user with email and password.
     *
     * <p><b>Process steps:</b></p>
     * <ul>
     *     <li>Verifies that the user exists</li>
     *     <li>Validates password using encryption</li>
     *     <li>Generates JWT token for authenticated user</li>
     * </ul>
     *
     * @param email    User's email address.
     * @param password Raw password entered by the user.
     * @return {@link AuthResponse} containing a valid JWT token.
     *
     * @throws ResourceNotFoundException If the email does not belong to a registered user.
     * @throws BadRequestException       If the password is incorrect.
     */
    public AuthResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
