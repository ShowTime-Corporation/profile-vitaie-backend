package showtime_corp.profile_vitaile.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import showtime_corp.profile_vitaile.entity.employability.Offer;

@Entity
@Table(name = "Employability")
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

    @Type(JsonType.class)
    @Column(name = "tech_offer", nullable = false, columnDefinition = "json")
    private Offer techOffer;

    @Type(JsonType.class)
    @Column(name = "education_offer", nullable = false, columnDefinition = "json")
    private Offer educationOffer;

    @Type(JsonType.class)
    @Column(name = "company_offer", nullable = false, columnDefinition = "json")
    private Offer companyOffer;
}
