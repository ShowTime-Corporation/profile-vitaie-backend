package showtime_corp.profile_vitaile.entity.employability;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a job offer or an employment opportunity.
 * This DTO is used to structure the JSON text returned to the frontend.
 */
@Data
public class Offer {

    /**
     * Unique identifier for the job offer.
     */
    private Long id;

    /**
     * The title of the job offer (e.g., "Software Engineer", "Marketing Manager").
     */
    private String title;

    /**
     * A detailed description of the job responsibilities and requirements.
     */
    private String description;

    /**
     * The name of the company offering the job.
     */
    private String companyName;

    /**
     * The location of the job (e.g., "New York, NY", "Remote").
     */
    private String location;

    /**
     * The salary range for the position (e.g., "50,000 - 70,000 USD per year").
     */
    private String salaryRange;

    /**
     * The type of employment (e.g., "Full-time", "Part-time", "Contract").
     */
    private String employmentType;

    /**
     * A list of skills required or preferred for the job.
     */
    private List<String> neededSkills;

    /**
     * The date when the job offer was posted.
     */
    private LocalDate postedDate;

    /**
     * A link to the application page for the job offer.
     */
    private String applicationLink;

}
