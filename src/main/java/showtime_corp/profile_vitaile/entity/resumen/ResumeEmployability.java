package showtime_corp.profile_vitaile.entity.resumen;

import lombok.Data;

import java.util.List;

/**
 * Represents the AI-generated employability analysis of the resume.
 * <p>
 * Stored as JSON in the column {@code resume_employability}.
 * Focuses on market fit, strengths, and improvement areas.
 * </p>
 */
@Data
public class ResumeEmployability {

    /**
     * Overall employability score or level.
     */
    private String employabilityLevel;

    /**
     * Strengths detected in the resume.
     */
    private List<String> strengths;

    /**
     * Weaknesses or missing elements affecting employability.
     */
    private List<String> weaknesses;

    /**
     * Suggested improvements to increase job opportunities.
     */
    private List<String> improvementSuggestions;

}
