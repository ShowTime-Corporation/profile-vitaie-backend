package showtime_corp.profile_vitaile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.dto.AdminUserRequestDTO;

import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.ConflictException;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;

import showtime_corp.profile_vitaile.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AdminUserService} interface.
 * <p>
 * Contains business logic for administrative user management, including
 * creation, retrieval, updates, and deletion of users.
 * </p>
 *
 * @author Samuel Monsalve
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final showtime_corp.profile_vitaile.mapper.UserMapper userMapper;

    /**
     * Retrieves a list of all registered users.
     *
     * @return a list of user response DTOs
     */
    @Override
    public List<showtime_corp.profile_vitaile.dto.UserProfileResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user's ID
     * @return the found user's details
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    @Override
    public showtime_corp.profile_vitaile.dto.UserProfileResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toUserResponseDTO(user);
    }

    /**
     * Updates an existing user's information.
     *
     * @param id      the ID of the user to update
     * @param request the DTO containing updated information
     * @return the updated user's details
     * @throws ResourceNotFoundException if the user does not exist
     * @throws ConflictException         if the new email is already taken by
     *                                   another user
     */
    @Override
    public showtime_corp.profile_vitaile.dto.UserProfileResponseDTO updateUser(Long id, AdminUserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Check if email is being changed and if it's already taken
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already active");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setSub(request.getSub());
        user.setActive(request.getActive());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDTO(savedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws ResourceNotFoundException if the user does not exist
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
