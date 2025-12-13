package showtime_corp.profile_vitaile.entity.user;

import java.time.LocalDate;

/**
 * Represents a single work experience entry of the user.
 * <p>
 * Stored as a list inside {@code user_experience}.
 * </p>
 */
public class UserExperience {

    /**
     * Job title or position.
     */
    private String role;

    /**
     * Company name.
     */
    private String company;

    /**
     * Start date of the position.
     */
    private LocalDate startDate;

    /**
     * End date of the position. Null if current.
     */
    private LocalDate endDate;

    /**
     * Indicates if this is the user's current job.
     */
    private boolean current;


}
