package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import showtime_corp.profile_vitaile.entity.User.UserSub;

/**
 * Data Transfer Object for Admin user operations.
 * <p>
 * This DTO is used to capture data for creating or updating users
 * through administrative endpoints.
 * </p>
 *
 * @author Samuel Monsalve
 * @version 1.0
 */
@Data
@Schema(description = "Request DTO for Admin operations on Users")
public class AdminUserRequestDTO {

    @Schema(description = "User first name", example = "Admin")
    @NotBlank(message = "First name cannot be empty")
    @Size(max = 200, message = "First name cannot exceed 200 characters")
    private String firstName;

    @Schema(description = "User last name", example = "User")
    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 200, message = "Last name cannot exceed 200 characters")
    private String lastName;

    @Schema(description = "User email", example = "admin@example.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @Size(max = 300, message = "Email cannot exceed 300 characters")
    private String email;

    @Schema(description = "User password. Optional for update (if empty, password remains unchanged).", example = "securePass123")
    private String password;

    @Schema(description = "User subscription/role", example = "ADMIN")
    @NotNull(message = "Role (sub) is required")
    private UserSub sub;

    @Schema(description = "User active status", example = "true")
    @NotNull(message = "Active status is required")
    private Boolean active;
}
