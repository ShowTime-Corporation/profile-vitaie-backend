package showtime_corp.profile_vitaile.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "resumen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Stores AI-generated resume representations.
 */
public class Resumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resumen_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "resume_info", nullable = false, columnDefinition = "TEXT")
    private String resumeInfo;

    @Column(name = "resume_employability", nullable = false, columnDefinition = "TEXT")
    private String employability;

    @Column(name = "resume_simple", nullable = false, columnDefinition = "TEXT")
    private String simple;

    @Column(name = "resume_recomendation", nullable = false, columnDefinition = "TEXT")
    private String recommendation;
}
