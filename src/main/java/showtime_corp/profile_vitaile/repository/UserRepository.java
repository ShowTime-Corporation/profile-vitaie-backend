package showtime_corp.profile_vitaile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import showtime_corp.profile_vitaile.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Check for duplacite email
    boolean existsByUserEmail(String email);
}
