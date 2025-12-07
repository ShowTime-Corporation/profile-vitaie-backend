package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.UserRepository;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    @Override
    public UserProfileResponseDTO getProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return buildResponse(user);
    }

    @Override
    public UserProfileResponseDTO updateProfile(Integer userId, UserProfileRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setTechStack(dto.getTechStack());
        user.setGithubUrl(dto.getGithubUrl());
        user.setExperience(dto.getExperience());

        userRepository.save(user);

        return buildResponse(user);
    }

    @Override
    public UserProfileResponseDTO uploadCv(Integer userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Use project root directory (safe absolute path)
            String uploadDir = System.getProperty("user.dir") + "/uploads/cv/";

            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = uploadDir + userId + "_" + file.getOriginalFilename();
            File dest = new File(filePath);

            file.transferTo(dest);

            user.setCvUrl(filePath);
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace(); // <-- shows real error in console
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }

        return buildResponse(user);
    }

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