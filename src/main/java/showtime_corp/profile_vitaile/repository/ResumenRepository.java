package showtime_corp.profile_vitaile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import showtime_corp.profile_vitaile.entity.Employability;
import showtime_corp.profile_vitaile.entity.Resumen;

import java.util.Optional;

@Repository
public interface ResumenRepository extends JpaRepository<Resumen, Integer> {
    Optional<Resumen> findByUserId(Integer userId);
}