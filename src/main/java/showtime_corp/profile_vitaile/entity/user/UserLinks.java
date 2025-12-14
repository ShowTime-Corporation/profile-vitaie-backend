package showtime_corp.profile_vitaile.entity.user;

import lombok.Data;

/**
 * Represents the user's social and professional links.
 * <p>
 * Stored as JSON in the {@code user_link} column.
 * </p>
 */
@Data
public class UserLinks {

    /**
     * Personal or portfolio website.
     */
    private String website;

    /**
     * GitHub username.
     */
    private String github;

    /**
     * LinkedIn username.
     */
    private String linkedin;


}
