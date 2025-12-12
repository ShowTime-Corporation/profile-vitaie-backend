package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
/**
 * DTO representing AI-generated roadmap.
 */
@Schema(description = "Roadmap AI-generated data")
public class RoadMapDTO {


    @NotNull(message = "user_id can not be empty")
    private Integer user_id;

    @Schema(
            description = "Professional analysis section",
            example = """
                    {
                      "summary": "Strong backend profile; needs cloud upskilling.",
                      "strengths": ["Java", "API Design"],
                      "weaknesses": ["Cloud Architecture"],
                      "skillGaps": ["Kubernetes", "AWS"],
                      "currentLevel": "Mid-level"
                    }
                    """
    )
    @NotNull(message = "road_analisis cannot be null")
    private Object road_analisis;

    @Schema(
            description = "AI-generated professional proposal",
            example = """
                    {
                      "mainGoal": "Become a Senior Cloud Backend Engineer",
                      "targetRoles": ["Backend Engineer", "Cloud Engineer"],
                      "recommendedSkills": ["Terraform", "AWS", "Kubernetes"],
                      "estimatedTimeline": "6 months"
                    }
                    """
    )
    @NotNull(message = "road_proposal cannot be null")
    private Object road_proposal;

    @Schema(
            description = "AI ideas and recommended actions",
            example = """
                    {
                      "projectIdeas": ["Deploy a serverless API", "Build a cloud-ready microservice"],
                      "learningActivities": ["AWS labs", "Kubernetes challenge"],
                      "portfolioIdeas": ["Add cloud case studies"]
                    }
                    """
    )
    @NotNull(message = "road_ideas cannot be null")
    private Object road_ideas;

    @Schema(
            description = "Things the user should keep doing",
            example = """
                    {
                      "keepSkills": ["Java", "Clean Code"],
                      "keepHabits": ["Daily coding"],
                      "keepTechnologies": ["Spring Boot"]
                    }
                    """
    )
    @NotNull(message = "road_keep cannot be null")
    private Object road_keep;
}