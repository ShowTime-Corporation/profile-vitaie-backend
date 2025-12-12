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
import showtime_corp.profile_vitaile.repository.UserRepository;

import java.io.File;
import java.io.IOException;

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

    /**
     * Retrieves the profile of a specific user by ID.
     *
     * @param userId the ID of the user whose profile is requested
     * @return the user's profile mapped to a DTO
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public UserProfileResponseDTO getProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return buildResponse(user);
    }

    /**
     * Updates the profile of the specified user.
     *
     * @param userId the user ID
     * @param dto the data to update in the user's profile
     * @return updated profile data as a DTO
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public UserProfileResponseDTO updateProfile(Integer userId, UserProfileRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setTechStack(dto.getTechStack());
        user.setGithubUrl(dto.getGithubUrl());
        user.setExperience(dto.getExperience());

        userRepository.save(user);

        return buildResponse(user);
    }

    /**
     * Uploads a CV file for a given user, stores it, and updates the CV URL in the user's profile.
     *
     * @param userId the user uploading the CV
     * @param file the uploaded file
     * @return the updated profile including the new CV URL
     * @throws ResourceNotFoundException if the user is not found
     * @throws UnprocessableEntityException if the file is empty or invalid
     * @throws InternalServerErrorException if any unexpected error occurs during file handling
     */
    @Override
    public UserProfileResponseDTO uploadCv(Integer userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (file == null || file.isEmpty()) {
            throw new UnprocessableEntityException("Uploaded file cannot be empty");
        }

        try {
            // Directory where CVs will be stored
            String uploadDir = System.getProperty("user.dir") + "/uploads/cv/";

            File folder = new File(uploadDir);
            if (!folder.exists() && !folder.mkdirs()) {
                throw new InternalServerErrorException("Could not create upload directory");
            }

            // Store file with prefix userID_ to avoid collisions
            String fileName = userId + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            File dest = new File(filePath);
            file.transferTo(dest);

            // (Optional but recommended) Only save a relative path in DB
            user.setCvUrl("/uploads/cv/" + fileName);
            userRepository.save(user);

        } catch (IOException e) {
            log.error("Error uploading CV for user {}: {}", userId, e.getMessage(), e);
            throw new InternalServerErrorException("Failed to upload CV");
        }

        return buildResponse(user);
    }

    /**
     * Maps a {@link User} entity to a {@link UserProfileResponseDTO}.
     *
     * @param user the user entity to map
     * @return a formatted DTO representing the user's profile
     */
    private UserProfileResponseDTO buildResponse(User user) {
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .techStack(user.getTechStack())
                .githubUrl(user.getGithubUrl())
                .experience(user.getExperience())
                .cvUrl(user.getCvUrl())
                .build();
    }
}
