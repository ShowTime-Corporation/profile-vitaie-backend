package showtime_corp.profile_vitaile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponseDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    private String phone;
    private String techStack;
    private String githubUrl;
    private String experience;

    private String cvUrl;
}
