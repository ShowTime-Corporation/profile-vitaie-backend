package showtime_corp.profile_vitaile.service;

import org.springframework.web.multipart.MultipartFile;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;

public interface UserProfileService {

    UserProfileResponseDTO getProfile(Integer userId);

    UserProfileResponseDTO updateProfile(Integer userId, UserProfileRequestDTO dto);

    UserProfileResponseDTO uploadCv(Integer userId, MultipartFile file);
}
