package showtime_corp.profile_vitaile.dto.mapper;


import org.mapstruct.Mapper;
import showtime_corp.profile_vitaile.dto.EmployabilityDTO;
import showtime_corp.profile_vitaile.entity.Employability;

@Mapper(componentModel = "spring")
public interface EmployabilityMapper {
    EmployabilityDTO toDto(Employability employability);
}
