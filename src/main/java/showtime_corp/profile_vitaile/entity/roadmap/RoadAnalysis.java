package showtime_corp.profile_vitaile.entity.roadmap;

import java.util.List;

/**
 * Represents the AI-generated analysis of the user's current professional state.
 * <p>
 * This object is stored as JSON in the column {@code road_analisis}.
 * It contains insights about strengths, weaknesses, gaps, and current level.
 * </p>
 */
public class RoadAnalysis {

    /**
     * General summary of the user's professional situation.
     */
    private String summary;

    /**
     * List of identified strengths.
     */
    private List<String> strengths;

    /**
     * List of identified weaknesses or improvement areas.
     */
    private List<String> weaknesses;

    /**
     * Detected skill gaps based on market requirements.
     */
    private List<String> skillGaps;

    /**
     * Estimated professional level (e.g., Junior, Mid, Senior).
     */
    private String currentLevel;

}