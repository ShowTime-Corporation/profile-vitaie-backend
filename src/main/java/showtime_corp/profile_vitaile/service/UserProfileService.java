package showtime_corp.profile_vitaile.service;

import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;

public interface UserProfileService {

    UserProfileResponseDTO getProfile(Long userId);
    UserProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO dto);
    UserProfileResponseDTO uploadCv(Long userId, MultipartFile file);
}
