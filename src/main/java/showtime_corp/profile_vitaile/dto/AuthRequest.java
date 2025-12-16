package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank//Refactor LoginRequest to AuthRequest
    private String email;
    @NotBlank
    private String password;
}
