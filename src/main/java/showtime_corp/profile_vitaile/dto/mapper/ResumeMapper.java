package showtime_corp.profile_vitaile.dto.mapper;


import org.mapstruct.Mapper;
import showtime_corp.profile_vitaile.dto.ResumenDTO;
import showtime_corp.profile_vitaile.entity.Resumen;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumenDTO toDto(Resumen resume);
}
