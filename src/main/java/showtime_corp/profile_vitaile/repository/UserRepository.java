package showtime_corp.profile_vitaile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import showtime_corp.profile_vitaile.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // Change integer Id to Long
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
