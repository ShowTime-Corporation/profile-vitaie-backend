package showtime_corp.profile_vitaile.service;

import showtime_corp.profile_vitaile.dto.AdminUserRequestDTO;

import java.util.List;

public interface AdminUserService {

    List<showtime_corp.profile_vitaile.dto.UserProfileResponseDTO> getAllUsers();

    showtime_corp.profile_vitaile.dto.UserProfileResponseDTO getUserById(Long id);

    showtime_corp.profile_vitaile.dto.UserProfileResponseDTO updateUser(Long id, AdminUserRequestDTO request);

    void deleteUser(Long id);
}
