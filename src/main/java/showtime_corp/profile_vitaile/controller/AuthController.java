package showtime_corp.profile_vitaile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.service.AuthService;

@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        authService.registerNewUser(request);
        return ResponseEntity.ok("User registered!");
    }
}
