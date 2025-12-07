package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
