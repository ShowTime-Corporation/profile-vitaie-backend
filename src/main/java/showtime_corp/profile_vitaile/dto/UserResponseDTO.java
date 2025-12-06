package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Integer user_id;

    private String first_name;

    private String last_name;

    private String user_email;

    private Boolean user_active;

    private String user_sub;
}
