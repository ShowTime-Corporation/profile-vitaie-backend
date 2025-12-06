package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class RoadMapResponseDTO {

    private Integer roadmap_id;

    private Integer user_id;

    private String road_analisis;

    private String road_proposal;

    private String road_ideas;

    private String road_keep;
}
