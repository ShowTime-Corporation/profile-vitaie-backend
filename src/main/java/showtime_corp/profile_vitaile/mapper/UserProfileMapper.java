package showtime_corp.profile_vitaile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import showtime_corp.profile_vitaile.dto.UserProfileRequestDTO;
import showtime_corp.profile_vitaile.dto.UserProfileResponseDTO;
import showtime_corp.profile_vitaile.entity.User;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponseDTO toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "sub", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateUserFromDto(UserProfileRequestDTO dto, @MappingTarget User user);
}

