package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployabilityRequestDTO {

    @NotNull(message = "user_id can not be empity")
    private Integer user_id;

    @NotBlank(message = "tech_offer can not be empity")
    private String tech_offer;

    @NotBlank(message = "education_offer can not be empity")
    private String education_offer;

    @NotBlank(message = "company_offer can not be empity")
    private String company_offer;
}
