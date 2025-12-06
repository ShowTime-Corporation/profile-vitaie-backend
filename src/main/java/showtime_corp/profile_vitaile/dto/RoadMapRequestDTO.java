package showtime_corp.profile_vitaile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoadMapRequestDTO {

    @NotNull(message = "user_id can not be empity")
    private Integer user_id;

    @NotBlank(message = "road_analisis can not be empity")
    private String road_analisis;

    @NotBlank(message = "road_proposal can not be empity")
    private String road_proposal;

    @NotBlank(message = "road_ideas can not be empity")
    private String road_ideas;

    @NotBlank(message = "road_keep can not be empity")
    private String road_keep;
}
