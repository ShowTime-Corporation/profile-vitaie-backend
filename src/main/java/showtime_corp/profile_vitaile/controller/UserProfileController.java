package showtime_corp.profile_vitaile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;
import showtime_corp.profile_vitaile.entity.Employability;
import showtime_corp.profile_vitaile.entity.Resumen;
import showtime_corp.profile_vitaile.entity.RoadMap;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.EmployabilityRepository;
import showtime_corp.profile_vitaile.repository.ResumenRepository;
import showtime_corp.profile_vitaile.repository.RoadMapRepository;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.service.UserProfileService;

/**
 * REST controller responsible for managing authenticated user profile data.
 *
 * <p>This includes retrieving the user's own profile, updating personal data,
 * and uploading a CV file. All operations rely on the authenticated user's identity
 * obtained through Spring Security's {@link Authentication} object.</p>
 *
 * <p>The controller delegates business logic to {@link UserProfileService}
 * and fetches user identifiers using {@link UserRepository}.</p>
 *
 * @author Andres Niebles y Santiago Toro
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Operations for retrieving and updating user profile data")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserRepository userRepository;
    private final ResumenRepository resumeRepository;
    private final RoadMapRepository roadmapRepository;
    private final EmployabilityRepository employabilityRepository;

    /**
     * Extracts the authenticated user's ID using their email address from {@link Authentication}.
     *
     * @param auth the authentication object containing the logged user's principal information
     * @return the user's database ID
     * @throws RuntimeException if the user is not found in the database
     */
    private Long getUserIdFromAuth(Authentication auth) { // Change Integer to Long
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @param auth the authentication token containing the logged user's identity
     * @return the user's profile data
     */
    @Operation(
            summary = "Get my profile",
            description = "Returns the profile information of the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile retrieved successfully",
                            content = @Content(schema = @Schema(implementation = UserProfileResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMyProfile(Authentication auth) {
        Long userId = getUserIdFromAuth(auth); // Change Integer to Long
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    /**
     * Updates the authenticated user's profile information.
     *
     * @param auth authentication data of the logged user
     * @param dto the updated profile fields
     * @return the updated profile information
     */
    @Operation(
            summary = "Update my profile",
            description = "Updates the profile data of the authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile updated successfully",
                            content = @Content(schema = @Schema(implementation = UserProfileResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateMyProfile(
            Authentication auth,
            @org.springframework.web.bind.annotation.RequestBody UserProfileRequestDTO dto) {

        Long userId = getUserIdFromAuth(auth); // Change Integer to Long
        return ResponseEntity.ok(userProfileService.updateProfile(userId, dto));
    }

    @Operation(
            summary = "Upload CV",
            description = "Uploads a CV file for the authenticated user and updates their profile.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CV uploaded and profile updated",
                            content = @Content(schema = @Schema(implementation = UserProfileResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid file format"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @PostMapping(
            value = "/me/upload-cv",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> analyzeCv(
            Authentication auth,
            @RequestPart("cv") MultipartFile cvPdf
    ) {

        Long userId = getUserIdFromAuth(auth);
        userProfileService.uploadCv(userId,cvPdf);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get resume by user ID",
            description = "Returns the AI-generated resume for the specified user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resume retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Resumen.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resume not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/resume")
    public ResponseEntity<Resumen> getResume(
            @Parameter(
                    description = "Unique identifier of the user",
                    required = true,
                    example = "1"
            )
            Authentication auth
    ) {

        Long userId = getUserIdFromAuth(auth);

        Resumen resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        return ResponseEntity.ok(resume);
    }


    @Operation(
            summary = "Get roadmap by user ID",
            description = "Returns the AI-generated career roadmap for the specified user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Roadmap retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoadMap.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Roadmap not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/roadmap")
    public ResponseEntity<RoadMap> getRoadMap(
            @Parameter(
                    description = "Unique identifier of the user",
                    required = true,
                    example = "1"
            )
            Authentication auth
    ) {

        Long userId = getUserIdFromAuth(auth);

        RoadMap roadMap = roadmapRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Roadmap not found"));

        return ResponseEntity.ok(roadMap);
    }


    @Operation(
            summary = "Get employability analysis by user ID",
            description = "Returns the AI-generated employability analysis for the specified user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Employability analysis retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Employability.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Employability analysis not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @GetMapping("/employability/")
    public ResponseEntity<Employability> getEmployability(
            @Parameter(
                    description = "Unique identifier of the user",
                    required = true,
                    example = "1"
            )
            Authentication auth
    ) {

        Long userId = getUserIdFromAuth(auth);
        Employability employability = employabilityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employability not found"));

        return ResponseEntity.ok(employability);
    }


}
