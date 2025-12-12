package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import showtime_corp.profile_vitaile.entity.user.UserEducation;
import showtime_corp.profile_vitaile.entity.user.UserLinks;
import showtime_corp.profile_vitaile.entity.user.UserExperience;
import showtime_corp.profile_vitaile.entity.user.UserSkills;
import io.hypersistence.utils.hibernate.type.json.JsonType;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Represents an application user.
 * <p>
 * Central entity that stores personal and professional profile data.
 * JSON fields are used for flexible and scalable profile sections.
 * </p>
 */
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;

    @Column(name = "user_email", nullable = false, unique = true, length = 300)
    private String email;

    @Column(name = "user_password", nullable = false, length = 255)
    private String password;

    @Column(name = "user_active", nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private UserSub sub;

    public enum UserSub { FREE, PREMIUM, ADMIN }

    @Column(name = "user_degree")
    private String degree;

    @Column(name = "user_location")
    private String location;

    @Column(name = "user_years")
    private Integer yearsOfExperience;

    @Column(name = "user_bio")
    private String bio;

    @Type(JsonType.class)
    @Column(name = "user_skills", columnDefinition = "json")
    private UserSkills skills;

    @Type(JsonType.class)
    @Column(name = "user_experience", columnDefinition = "json")
    private List<UserExperience> experience; // List<UserExperience>

    @Type(JsonType.class)
    @Column(name = "user_education", columnDefinition = "json")
    private List<UserEducation> education;

    @Type(JsonType.class)
    @Column(name = "user_link", columnDefinition = "json")
    private UserLinks links;

    @Type(JsonType.class)
    @Column(name = "user_pdf", columnDefinition = "json")
    private String pdf;

    // Spring Security UserDetails methods
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + sub.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return active; }
}