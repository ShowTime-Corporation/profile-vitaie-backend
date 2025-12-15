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
            example = "Current Role: Junior Backend Dev. Goal: Senior Data Scientist in 3 years."
    )
    @NotNull(message = "road_analisis cannot be null")
    private Object road_analisis;

    @Schema(
            description = "AI-generated professional proposal",
            example = "Phase 1 (6 months): Master Python Data Libraries (Pandas/Numpy). Phase 2: Complete ML Bootcamp."
    )
    @NotNull(message = "road_proposal cannot be null")
    private Object road_proposal;

    @Schema(
            description = "AI ideas and recommended actions",
            example = "Action: Start a side project using live data streams. Idea: Network with Data Leads on LinkedIn."
    )
    @NotNull(message = "road_ideas cannot be null")
    private Object road_ideas;

    @Schema(
            description = "Things the user should keep doing",
            example = "Continue contributing to Open Source projects (GitHub). Maintain weekly study schedule (10h)."
    )
    @NotNull(message = "road_keep cannot be null")
    private Object road_keep;
}