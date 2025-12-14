package showtime_corp.profile_vitaile.entity.user;

import lombok.Data;

import java.util.List;

/**
 * Represents the user's technical and professional skills.
 * <p>
 * Stored as JSON in the {@code user_skills} column.
 * This structure matches the skills section in the user profile UI.
 * </p>
 */
@Data
public class UserSkills {

    /**
     * List of user skills (e.g., React, TypeScript, AWS).
     */
    private List<String> skills;

}
