    package showtime_corp.profile_vitaile.entity.user;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.Data;

    import java.time.YearMonth;

    /**
     * Represents a single work experience entry of the user.
     * <p>
     * Stored as a list inside {@code user_experience}.
     * </p>
     */
    @Data
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
        @JsonFormat(pattern = "yyyy-MM")
        private YearMonth startDate;

        /**
         * End date of the position. Null if current.
         */
        @JsonFormat(pattern = "yyyy-MM")
        private YearMonth endDate;

        /**
         * Indicates if this is the user's current job.
         */
        private boolean current;

        /**
         * Description of responsibilities and achievements in the role.
         */
        private String description;

    }
