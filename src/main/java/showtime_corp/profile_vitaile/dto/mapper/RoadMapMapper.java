package showtime_corp.profile_vitaile.dto.mapper;

import org.mapstruct.Mapper;
import showtime_corp.profile_vitaile.dto.RoadMapDTO;
import showtime_corp.profile_vitaile.entity.RoadMap;

@Mapper(componentModel = "spring")
public interface RoadMapMapper {
    RoadMapDTO toDto(RoadMap roadMap);
}
