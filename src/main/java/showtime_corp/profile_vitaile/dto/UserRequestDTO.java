package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name can not be empty")
    @Size(max = 200, message = "Name can not be more than 200 characters")
    private String first_name;

    @NotBlank(message = "Lastname can not be empty")
    @Size(max = 200, message = "Lastname can not be more than 200 characters")
    private String last_name;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Invalid email format")
    @Size(max = 300)
    private String user_email;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, max = 200, message = "Password must be between 6 and 200 characters")
    private String user_password;

    @NotNull(message = "Role can not be null")
    private String user_sub; // FREE, PREMIUM, ADMIN
}
