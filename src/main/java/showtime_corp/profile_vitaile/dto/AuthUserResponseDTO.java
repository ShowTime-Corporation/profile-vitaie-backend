package showtime_corp.profile_vitaile.dto;

import lombok.Data;
import showtime_corp.profile_vitaile.entity.User;

@Data
public class AuthUserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private User.UserSub sub;
}
