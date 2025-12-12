package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
            example = """
                    {
                      "fileName": "cv_john.pdf",
                      "fileUrl": "https://cdn.mysite.com/cv/cv_john.pdf",
                      "uploadedAt": "2025-01-01T11:00:00Z"
                    }
                    """
    )
    private Object pdf;
}
