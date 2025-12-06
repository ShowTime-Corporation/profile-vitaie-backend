package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String user_email;

    private String user_password;

    private Boolean user_active = true;

    @Enumerated(EnumType.STRING)
    private UserSub user_sub;

    public enum UserSub {
        FREE, PREMIUM, ADMIN
    }
}
