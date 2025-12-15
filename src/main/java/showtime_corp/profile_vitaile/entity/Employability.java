package showtime_corp.profile_vitaile.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "employability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Stores AI-generated employability analysis for a user.
 */
public class Employability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employability_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "tech_offer", nullable = false, columnDefinition = "TEXT")
    private String techOffer;

    @Column(name = "education_offer", nullable = false, columnDefinition = "TEXT")
    private String educationOffer;

    @Column(name = "company_offer", nullable = false, columnDefinition = "TEXT")
    private String companyOffer;
}
