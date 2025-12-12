package showtime_corp.profile_vitaile.entity.resumen;

import java.util.List;

/**
 * Represents AI-generated recommendations to improve the resume.
 * <p>
 * Stored as JSON in the column {@code resume_recomendation}.
 * Includes actionable advice and optimization tips.
 * </p>
 */
public class ResumeRecommendation {

    /**
     * General feedback provided by the AI.
     */
    private String generalFeedback;

    /**
     * Content-related improvement suggestions.
     */
    private List<String> contentRecommendations;

    /**
     * Formatting and structure suggestions.
     */
    private List<String> formatRecommendations;

    /**
     * Keywords recommended to improve ATS compatibility.
     */
    private List<String> atsKeywords;

}
