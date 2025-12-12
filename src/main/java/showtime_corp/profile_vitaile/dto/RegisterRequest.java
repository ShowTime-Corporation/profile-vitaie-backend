package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
