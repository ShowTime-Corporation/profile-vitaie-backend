package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import showtime_corp.profile_vitaile.entity.user.UserEducation;
import showtime_corp.profile_vitaile.entity.user.UserExperience;

import java.util.List;

@Data
@Schema(description = "User response data without sensitive fields (password is excluded)")
public class UserResponseDTO {

    @Schema(
            description = "Unique user ID",
            example = "15"
    )
    private Long user_id;

    @Schema(
            description = "User first name",
            example = "John"
    )
    private String first_name;

    @Schema(
            description = "User last name",
            example = "Doe"
    )
    private String last_name;

    @Schema(
            description = "Email used for login and notifications",
            example = "john.doe@example.com"
    )
    private String user_email;

    @Schema(
            description = "User subscription type",
            example = "FREE"
    )
    private String user_sub;

    @Schema(
            description = "Whether the user is active",
            example = "true"
    )
    private Boolean user_active;

    @Schema(
            description = "Biography or personal description",
            example = "Software developer passionate about clean architecture."
    )
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

    @Schema(
            description = "Uploaded CV/PDF information",
            example = ""
    )
    private String pdf;
}
