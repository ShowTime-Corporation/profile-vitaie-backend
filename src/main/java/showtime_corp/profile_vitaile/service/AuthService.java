package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.dto.AuthUserResponseDTO;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.BadRequestException;
import showtime_corp.profile_vitaile.exception.ConflictException;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;
import showtime_corp.profile_vitaile.mapper.UserMapper;
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
    private final UserMapper userMapper;

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
     * @return A DTO representing the newly created user.
     *
     * @throws ConflictException If the email is already in use.
     */
    public AuthUserResponseDTO registerNewUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        // Map incoming request to User entity using MapStruct
        User user = userMapper.fromRegisterRequest(request);

        // Encrypt and set password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set default user properties
        user.setActive(true);
        user.setSub(User.UserSub.FREE);

        // Persist user and return mapped response DTO
        return userMapper.toAuthUserResponse(userRepository.save(user));
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
     * @return {@link AuthResponse} containing a valid JWT token and user data.
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

        // Generate JWT token based on authenticated user
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(userMapper.toAuthUserResponse(user))
                .build();
    }
}
