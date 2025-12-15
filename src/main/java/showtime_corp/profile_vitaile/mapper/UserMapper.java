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

    @Mapping(target = "user_id", source = "id")
    @Mapping(target = "first_name", source = "firstName")
    @Mapping(target = "last_name", source = "lastName")
    @Mapping(target = "user_email", source = "email")
    @Mapping(target = "user_sub", source = "sub")
    @Mapping(target = "user_active", source = "active")
    showtime_corp.profile_vitaile.dto.UserProfileResponseDTO toUserResponseDTO(User user);
}
