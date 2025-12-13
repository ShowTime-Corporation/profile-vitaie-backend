package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class AuthRequest { //Refactor LoginRequest to AuthRequest
    private String email;
    private String password;
}
