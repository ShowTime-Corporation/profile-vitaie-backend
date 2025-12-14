package showtime_corp.profile_vitaile.entity.user;

import lombok.Data;

/**
 * Represents an education entry of the user.
 * <p>
 * Stored as a list inside {@code user_education}.
 * </p>
 */
@Data
public class UserEducation {

    /**
     * Degree obtained.
     */
    private String degree;

    /**
     * Institution name.
     */
    private String institution;

    /**
     * Graduation year or description.
     */
    private String graduationYear;


}
