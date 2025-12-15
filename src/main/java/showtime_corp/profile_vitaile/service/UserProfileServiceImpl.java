package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.InternalServerErrorException;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;
import showtime_corp.profile_vitaile.exception.UnprocessableEntityException;
import showtime_corp.profile_vitaile.mapper.UserProfileMapper;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.service.service_ai.EmployabilityAiService;
import showtime_corp.profile_vitaile.service.service_ai.ResumeAiService;
import showtime_corp.profile_vitaile.service.service_ai.RoadMapAiService;

/**
 * Service implementation for managing user profile operations such as retrieving profile data,
 * updating user information, and uploading a CV file.
 *
 * <p>This service interacts with {@link UserRepository} to access and modify the user's stored data.
 * It handles validation, error propagation, file upload logic, and response mapping.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;
    private final CvExtractionService extractionService;
    private final EmployabilityAiService employabilityService;
    private final RoadMapAiService roadMapService;
    private final ResumeAiService resumeService;

    /**
     * Retrieves the profile of a specific user by ID.
     *
     * @param userId the ID of the user whose profile is requested
     * @return the user's profile mapped to a DTO
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public UserProfileResponseDTO getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Map entity to response DTO using MapStruct
        return userProfileMapper.toResponse(user);
    }

    /**
     * Updates the profile of the specified user.
     *
     * @param userId the user ID
     * @param dto    the data to update in the user's profile
     * @return updated profile data as a DTO
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public UserProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Apply incoming changes to existing user entity
        userProfileMapper.updateUserFromDto(dto, user);
        userRepository.save(user);

        return userProfileMapper.toResponse(user);
    }

    /**
     * Uploads a CV file for a given user, stores it, and updates the CV URL in the user's profile.
     *
     * @param userId the user uploading the CV
     * @param file   the uploaded file
     * @return the updated profile including the new CV URL
     * @throws ResourceNotFoundException    if the user is not found
     * @throws UnprocessableEntityException if the file is empty or invalid
     * @throws InternalServerErrorException if any unexpected error occurs during file handling
     */
    @Override
    public UserProfileResponseDTO uploadCv(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (file == null || file.isEmpty()) {
            throw new UnprocessableEntityException("Uploaded file cannot be empty");
        }

        try {
            // Validate file type (pdf)
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                throw new UnprocessableEntityException("Only PDF files are allowed");
            }

            // Validate file size (max 10MB)
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new UnprocessableEntityException("File size exceeds the maximum limit of 10MB");
            }

            // extraction service parses the PDF.
            String cvText = extractionService.extractText(file);

            // Then, we call the IA analysis.
            employabilityService.generate(user, cvText);
            roadMapService.generate(user, cvText);
            resumeService.generate(user, cvText);

        } catch (Error e) {
            log.error("Error uploading CV for user {}: {}", userId, e.getMessage(), e);
            throw new InternalServerErrorException("Failed to upload CV");
        }

        return userProfileMapper.toResponse(user);
    }
}
