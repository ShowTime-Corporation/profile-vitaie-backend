package showtime_corp.profile_vitaile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
/**
 * DTO representing AI-generated resumen.
 */
@Schema(description = "Resumen AI-generated data")
public class ResumenDTO {

    @NotNull(message = "user_id can not be empty")
    private Integer user_id;

    @Schema(
            description = "Resume info",
            example = "Extracted keywords: Python, AWS, SQL, Project Management. Experience: 5 years."
    )
    @NotNull(message = "resume_info cannot be null")
    private Object resume_info;

    @Schema(
            description = "Employability analysis for the resume",
            example = "Match rate: 80% for Data Analyst roles. Weakness: Lack of soft skills section."
    )
    @NotNull(message = "resume_employability cannot be null")
    private Object resume_employability;

    @Schema(
            description = "Simple résumé model",
            example = "Generated model summary: Focused on achievements, not tasks. Uses a chronological format."
    )
    @NotNull(message = "resume_simple cannot be null")
    private Object resume_simple;

    @Schema(
            description = "AI-generated improvement recommendations",
            example = "Improvement: Quantify 3 achievements in the 'Experience' section. Add a LinkedIn profile URL."
    )
    @NotNull(message = "resume_recomendation cannot be null")
    private Object resume_recomendation;
}