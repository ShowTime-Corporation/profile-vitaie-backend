package showtime_corp.profile_vitaile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import showtime_corp.profile_vitaile.dto.AuthResponse;
import showtime_corp.profile_vitaile.dto.LoginRequest;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        authService.registerNewUser(request);
        return ResponseEntity.ok("User registered!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

}
