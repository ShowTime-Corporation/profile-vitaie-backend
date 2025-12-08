package showtime_corp.profile_vitaile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.service.UserProfileService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    // Extract user email from authentication and fetch user ID from database
    private Integer getUserIdFromAuth(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    // Return logged user's profile data
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMyProfile(Authentication auth) {
        Integer userId = getUserIdFromAuth(auth);
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    // Update logged user's profile information
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateMyProfile(
            Authentication auth,
            @RequestBody UserProfileRequestDTO dto) {

        Integer userId = getUserIdFromAuth(auth);
        return ResponseEntity.ok(userProfileService.updateProfile(userId, dto));
    }

    // Upload CV file and update user profile with CV URL
    @PostMapping("/me/upload-cv")
    public ResponseEntity<UserProfileResponseDTO> uploadCv(
            Authentication auth,
            @RequestParam("file") MultipartFile file) {

        Integer userId = getUserIdFromAuth(auth);
        return ResponseEntity.ok(userProfileService.uploadCv(userId, file));
    }
}
