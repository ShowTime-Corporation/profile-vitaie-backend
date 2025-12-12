package showtime_corp.profile_vitaile.entity.roadmap;

import java.util.List;

/**
 * Represents the aspects that the user should maintain and reinforce.
 * <p>
 * Stored as JSON in the column {@code road_keep}.
 * Focuses on habits, skills, and practices that are already valuable.
 * </p>
 */
public class RoadKeep {

    /**
     * Skills that should be maintained.
     */
    private List<String> keepSkills;

    /**
     * Positive habits or practices to continue.
     */
    private List<String> keepHabits;

    /**
     * Technologies or tools the user should keep using.
     */
    private List<String> keepTechnologies;


}
