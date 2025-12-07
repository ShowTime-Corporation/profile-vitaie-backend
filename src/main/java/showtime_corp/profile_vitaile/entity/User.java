package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private UserSub sub;

    public enum UserSub { FREE, PREMIUM, ADMIN }


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Resumen resumen;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RoadMap roadMap;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employability employability;


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