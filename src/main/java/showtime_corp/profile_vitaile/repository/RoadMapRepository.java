package showtime_corp.profile_vitaile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import showtime_corp.profile_vitaile.entity.Employability;
import showtime_corp.profile_vitaile.entity.RoadMap;

import java.util.Optional;

@Repository
public interface RoadMapRepository extends JpaRepository<RoadMap, Integer> {
    Optional<RoadMap> findByUserId(Integer userId);
}