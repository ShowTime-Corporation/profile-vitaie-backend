package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Data;

import java.util.List;

@Data
@Schema(description = "User profile data")

/**
 * DTO used for creating or updating a user's profile.
 */
public class UserRequestDTO {


    @Schema(
            description = "User first name",
            example = "John"
    )
    @NotBlank(message = "Name can not be empty")
    @Size(max = 200, message = "Name can not be more than 200 characters")
    private String first_name;

    @Schema(
            description = "User last name",
            example = "Doe"
    )
    @NotBlank(message = "Lastname can not be empty")
    @Size(max = 200, message = "Lastname can not be more than 200 characters")
    private String last_name;

    @Schema(
            description = "Email used for login and notifications",
            example = "john.doe@example.com"
    )
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Invalid email format")
    @Size(max = 300)
    private String user_email;


    @Schema(
            description = "User password",
            example = "password123"
    )
    @NotBlank(message = "Password can not be empty")
    @Size(min = 6, max = 200, message = "Password must be between 6 and 200 characters")
    private String user_password;

    @Schema(
            description = "User role",
            example = "FREE"
    )
    @NotNull(message = "Role can not be null")
    private String user_sub; // FREE, PREMIUM, ADMIN

    @Schema(
            description = "Biography or personal description",
            example = "Software developer passionate about clean architecture."
    )
    @Size(max = 1000)
    private String bio;

    @Schema(
            description = "User academic degree",
            example = "Bachelor in Computer Science"
    )
    private String degree;

    @Schema(
            description = "User geographic location",
            example = "Medellín, Colombia"
    )
    private String location;

    @Schema(
            description = "Years of professional experience",
            example = "4"
    )
    @Min(0)
    @Max(60)
    private Integer yearsOfExperience;

    @Schema(
            description = "List of work experience items",
            example = """
                    [
                      {
                        "role": "Backend Developer",
                        "company": "Globant",
                        "startDate": "2022-01-01",
                        "endDate": "2024-02-01",
                        "current": false
                      }
                    ]
                    """
    )
    private List<Object> experience;

    @Schema(
            description = "Educational background",
            example = """
                    [
                      {
                        "degree": "Software Engineering",
                        "institution": "University of Medellín",
                        "graduationYear": "2021"
                      }
                    ]
                    """
    )
    private List<Object> education;

    @Schema(
            description = "User social and professional links",
            example = """
                    {
                      "website": "https://portfolio.com/john",
                      "github": "johnDev",
                      "linkedin": "john-doe",
                      "twitter": "john_codes"
                    }
                    """
    )
    private Object links;

    @Schema(
            description = "Uploaded CV/PDF information",
            example = "*JSON*"
    )
    private String pdf;

}
