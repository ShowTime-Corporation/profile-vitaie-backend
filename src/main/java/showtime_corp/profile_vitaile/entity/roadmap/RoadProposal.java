package showtime_corp.profile_vitaile.entity.roadmap;

import lombok.Data;

import java.util.List;

/**
 * Represents the AI-generated professional growth proposal.
 * <p>
 * Stored as JSON in the column {@code road_proposal}.
 * Defines objectives and recommendations to improve the user's career path.
 * </p>
 */
@Data
public class RoadProposal {

    /**
     * Main professional objective proposed by the AI.
     */
    private String mainGoal;

    /**
     * Suggested roles or positions to aim for.
     */
    private List<String> targetRoles;

    /**
     * Recommended technologies or skills to learn.
     */
    private List<String> recommendedSkills;

    /**
     * Estimated time frame to achieve the proposal.
     */
    private String estimatedTimeline;

}
