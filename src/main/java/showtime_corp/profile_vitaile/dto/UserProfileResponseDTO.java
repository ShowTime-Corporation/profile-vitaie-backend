package showtime_corp.profile_vitaile.dto;

import lombok.Builder;
import lombok.Data;
import showtime_corp.profile_vitaile.entity.user.UserEducation;
import showtime_corp.profile_vitaile.entity.user.UserExperience;
import showtime_corp.profile_vitaile.entity.user.UserSkills;

import java.util.List;

@Data
@Builder
public class UserProfileResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String degree;
    private String location;
    private Integer yearsOfExperience;
    private String bio;
    private UserSkills skills;
    private List<UserExperience> experience;
    private List<UserEducation> education;
    private String pdf;
}
