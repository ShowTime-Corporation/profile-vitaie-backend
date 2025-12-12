package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Employability AI response")
/**
 * DTO representing AI-generated employability results.
 */
public class EmployabilityDTO {


    @NotNull(message = "employability_id can not be empty")
    private Integer employability_id;

    @NotNull(message = "user_id can not be empty")
    private Integer user_id;

    @Schema(
            description = "Tech-related employability analysis",
            example = """
                    {
                      "roles": ["Backend Developer", "DevOps Engineer"],
                      "requiredSkills": ["Java", "Docker", "Kubernetes"],
                      "technologies": ["AWS", "PostgreSQL"],
                      "demandLevel": "High"
                    }
                    """
    )
    @NotNull(message = "tech_offer cannot be null")
    private Object tech_offer;

    @Schema(
            description = "Education recommendations from AI",
            example = """
                    {
                      "courses": ["AWS Cloud Practitioner", "Kubernetes Bootcamp"],
                      "certifications": ["AWS CCP", "CKA"],
                      "platforms": ["Udemy", "Coursera"],
                      "estimatedDuration": "3 months"
                    }
                    """
    )
    @NotNull(message = "education_offer cannot be null")
    private Object education_offer;

    @Schema(
            description = "Company and market recommendations",
            example = """
                    {
                      "companies": ["Globant", "Rappi"],
                      "sectors": ["Fintech", "Cloud"],
                      "workModels": ["Remote", "Hybrid"],
                      "benefits": ["Health insurance", "Education stipend"]
                    }
                    """
    )
    @NotNull(message = "company_offer cannot be null")
    private Object company_offer;
}
