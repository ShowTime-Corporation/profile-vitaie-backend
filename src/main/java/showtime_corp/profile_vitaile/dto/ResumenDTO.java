package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResumenDTO {

    @NotNull(message = "user_id can not be empty")
    private Integer user_id;

    @Schema(
            description = "Resume info",
            example = """
                    {
                      "title": "Backend Engineer",
                      "summary": "Backend developer with Spring and microservices experience.",
                      "skills": ["Java", "Spring Boot", "Docker"],
                      "experience": ["Developed APIs", "Optimized microservices"],
                      "education": ["Software Engineering - UdeA"]
                    }
                    """
    )
    @NotNull(message = "resume_info cannot be null")
    private Object resume_info;

    @Schema(
            description = "Employability analysis for the resume",
            example = """
                    {
                      "employabilityLevel": "Mid-level",
                      "strengths": ["Clean code", "Microservices"],
                      "weaknesses": ["Cloud"],
                      "improvementSuggestions": ["Add measurable achievements"]
                    }
                    """
    )
    @NotNull(message = "resume_employability cannot be null")
    private Object resume_employability;

    @Schema(
            description = "Simple résumé model",
            example = """
                    {
                      "fullName": "John Doe",
                      "role": "Backend Developer",
                      "shortDescription": "Backend specialist with 4 years of experience.",
                      "contactInfo": "john@example.com"
                    }
                    """
    )
    @NotNull(message = "resume_simple cannot be null")
    private Object resume_simple;

    @Schema(
            description = "AI-generated improvement recommendations",
            example = """
                    {
                      "generalFeedback": "Good structure but weak impact metrics.",
                      "contentRecommendations": ["Add achievements"],
                      "formatRecommendations": ["Improve spacing"],
                      "atsKeywords": ["REST", "CI/CD", "Microservices"]
                    }
                    """
    )
    @NotNull(message = "resume_recomendation cannot be null")
    private Object resume_recomendation;
}
