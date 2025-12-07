package showtime_corp.profile_vitaile.dto;

import lombok.Data;

@Data
public class UserProfileRequestDTO {

    private String firstName;
    private String lastName;
    private String phone;
    private String techStack;
    private String githubUrl;
    private String experience;

}
