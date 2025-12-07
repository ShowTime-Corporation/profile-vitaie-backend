package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class EmployabilityResponseDTO {

    private Integer employability_id;

    private Integer user_id;

    private String tech_offer;

    private String education_offer;

    private String company_offer;
}