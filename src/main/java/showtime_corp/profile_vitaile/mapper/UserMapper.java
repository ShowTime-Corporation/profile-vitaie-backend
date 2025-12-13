package showtime_corp.profile_vitaile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import showtime_corp.profile_vitaile.dto.AuthUserResponseDTO;
import showtime_corp.profile_vitaile.dto.RegisterRequest;
import showtime_corp.profile_vitaile.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuthUserResponseDTO toAuthUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "experience", ignore = true)
    @Mapping(target = "education", ignore = true)
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "pdf", ignore = true)
    User fromRegisterRequest(RegisterRequest request);
}
