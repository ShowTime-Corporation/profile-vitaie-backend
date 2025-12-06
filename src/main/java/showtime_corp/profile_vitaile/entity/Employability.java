package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Employability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employability_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String tech_offer;

    @Column(columnDefinition = "TEXT")
    private String education_offer;

    @Column(columnDefinition = "TEXT")
    private String company_offer;
}
