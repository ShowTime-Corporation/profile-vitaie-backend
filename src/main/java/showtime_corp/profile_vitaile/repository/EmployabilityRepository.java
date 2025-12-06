package showtime_corp.profile_vitaile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import showtime_corp.profile_vitaile.entity.Employability;

@Repository
public interface EmployabilityRepository extends JpaRepository<Employability, Integer> {

}
