package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User registerNewUser(RegisterRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        user.setActive(true);
        user.setSub(User.UserSub.FREE);
        user.setResumen(null);
        user.setRoadMap(null);
        user.setEmployability(null);


        return userRepository.save(user);
    }

    public AuthResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
