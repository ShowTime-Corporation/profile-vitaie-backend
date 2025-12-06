package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class ResumenResponseDTO {

    private Integer resumen_id;

    private Integer user_id;

    private String resume_info;

    private String resume_employability;

    private String resume_simple;

    private String resume_recomendation;
}
