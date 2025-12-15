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
            example = "Compatible with Full Stack (Python/React) 85%. Salary range: 40k-50k USD."
    )
    @NotNull(message = "tech_offer cannot be null")
    private Object tech_offer;

    @Schema(
            description = "Education recommendations from AI",
            example = "Recommendation: AWS Cloud Certification. Gap: Lack of Kubernetes experience."
    )
    @NotNull(message = "education_offer cannot be null")
    private Object education_offer;

    @Schema(
            description = "Company and market recommendations",
            example = "Recommended: Fintech companies (Nubank). Trend: High demand in AI/ML."
    )
    @NotNull(message = "company_offer cannot be null")
    private Object company_offer;
}