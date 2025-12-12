package showtime_corp.profile_vitaile.entity.roadmap;

import java.util.List;

/**
 * Represents AI-generated ideas to strengthen the user's professional profile.
 * <p>
 * Stored as JSON in the column {@code road_ideas}.
 * Includes projects, learning activities, and portfolio suggestions.
 * </p>
 */
public class RoadIdeas {

    /**
     * Suggested personal or professional projects.
     */
    private List<String> projectIdeas;

    /**
     * Recommended learning activities (courses, certifications, challenges).
     */
    private List<String> learningActivities;

    /**
     * Portfolio improvement suggestions.
     */
    private List<String> portfolioIdeas;

}
