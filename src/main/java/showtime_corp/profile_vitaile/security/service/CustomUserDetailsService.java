package showtime_corp.profile_vitaile.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.UserRepository;
import showtime_corp.profile_vitaile.security.details.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}


