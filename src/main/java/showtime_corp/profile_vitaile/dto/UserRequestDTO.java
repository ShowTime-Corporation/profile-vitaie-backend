package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import showtime_corp.profile_vitaile.entity.user.UserEducation;
import showtime_corp.profile_vitaile.entity.user.UserExperience;
import showtime_corp.profile_vitaile.entity.user.UserLinks;
import showtime_corp.profile_vitaile.entity.user.UserSkills;

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
    private String firstName;

    @Schema(
            description = "User last name",
            example = "Doe"
    )
    @NotBlank(message = "Lastname can not be empty")
    @Size(max = 200, message = "Lastname can not be more than 200 characters")
    private String lastName;

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
            description = "User skills",
            example = """
                    {
                      "languages": ["Java", "Spring Boot"],
                      "tools": ["Docker", "Postman"]
                    }
                    """
    )
    private UserSkills skills;

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
    private List<UserExperience> experience;

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
    private List<UserEducation> education;

}
