package showtime_corp.profile_vitaile.entity.resumen;

import lombok.Data;

/**
 * Represents a simplified version of AI-generated recommendations to improve the resume.
 * <p>
 * Stored as JSON in the column {@code resume_simple}.
 * This version is intended for free users and provides limited feedback.
 * </p>
 */
@Data
public class ResumeSimple {

    /**
     * General feedback provided by the AI.
     */
    private String generalFeedback;

}
