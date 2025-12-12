package showtime_corp.profile_vitaile.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.exception.ResourceNotFoundException;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.security.details.CustomUserDetails;

/**
 * Service responsible for loading user information from the database
 * and adapting it to Spring Security's {@link UserDetails} model.
 *
 * <p>This service is used by Spring Security during authentication and by
 * the JWT filter when validating tokens.</p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /** Repository used to retrieve user information. */
    private final UserRepository repo;

    /**
     * Loads a user by email (used as the username in this system).
     *
     * @param email The email of the user attempting to authenticate.
     * @return A {@link CustomUserDetails} instance containing user authentication data.
     * @throws UsernameNotFoundException If no user exists with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }
}
