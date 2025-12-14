package showtime_corp.profile_vitaile.service;

import showtime_corp.profile_vitaile.dto.AdminUserRequestDTO;
import showtime_corp.profile_vitaile.dto.UserResponseDTO;

import java.util.List;

public interface AdminUserService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, AdminUserRequestDTO request);

    void deleteUser(Long id);
}
