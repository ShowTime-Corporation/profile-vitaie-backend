package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResumenRequestDTO {

    @NotNull(message = "user_id can not be empty")
    private Integer user_id;

    @NotBlank(message = "resume_info can not be empty")
    private String resume_info;

    @NotBlank(message = "resume_employability can not be empty")
    private String resume_employability;

    @NotBlank(message = "resume_simple can not be empty")
    private String resume_simple;

    @NotBlank(message = "resume_recomendation can not be empty")
    private String resume_recomendation;
}
