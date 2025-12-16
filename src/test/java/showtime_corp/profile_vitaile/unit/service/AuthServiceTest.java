package showtime_corp.profile_vitaile.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.AuthUserResponseDTO;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.ConflictException;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;
import showtime_corp.profile_vitaile.exception.UnauthorizedException;
import showtime_corp.profile_vitaile.mapper.UserMapper;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.security.jwt.JwtService;
import showtime_corp.profile_vitaile.service.AuthService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private User user;
    private AuthUserResponseDTO authUserResponseDTO;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@mail.com");
        registerRequest.setPassword("123456");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");

        user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        user.setPassword("encodedPass");
        user.setSub(User.UserSub.FREE);
        user.setActive(true);

        authUserResponseDTO = new AuthUserResponseDTO();
        authUserResponseDTO.setEmail("test@mail.com");
    }

    // ======================
    // REGISTER
    // ======================

    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterNewUser() {

        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);

        when(userMapper.fromRegisterRequest(registerRequest))
                .thenReturn(user);

        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPass");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        when(userMapper.toAuthUserResponse(user))
                .thenReturn(authUserResponseDTO);

        AuthUserResponseDTO response =
                authService.registerNewUser(registerRequest);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("test@mail.com");

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("123456");
    }

    @Test
    @DisplayName("Should throw ConflictException when email already exists")
    void shouldFailRegisterWhenEmailExists() {

        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(true);

        assertThrows(
                ConflictException.class,
                () -> authService.registerNewUser(registerRequest)
        );

        verify(userRepository, never()).save(any());
    }

    // ======================
    // LOGIN
    // ======================

    @Test
    @DisplayName("Should login user successfully")
    void shouldLoginUser() {

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        when(userMapper.toAuthUserResponse(user))
                .thenReturn(authUserResponseDTO);

        AuthResponse response =
                authService.login("test@mail.com", "123456");

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getUser().getEmail()).isEqualTo("test@mail.com");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user does not exist")
    void shouldFailLoginWhenUserNotFound() {

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login("test@mail.com", "123456")
        );
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when password is invalid")
    void shouldFailLoginWhenPasswordIsInvalid() {

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", user.getPassword()))
                .thenReturn(false);

        assertThrows(
                UnauthorizedException.class,
                () -> authService.login("test@mail.com", "wrong")
        );
    }

}
